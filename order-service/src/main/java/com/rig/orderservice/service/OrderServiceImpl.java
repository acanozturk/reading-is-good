package com.rig.orderservice.service;

import com.rig.orderservice.cache.Cache;
import com.rig.orderservice.data.constant.OrderStatus;
import com.rig.orderservice.data.entity.Book;
import com.rig.orderservice.data.entity.Customer;
import com.rig.orderservice.data.entity.CustomerAddress;
import com.rig.orderservice.data.entity.Order;
import com.rig.orderservice.data.payload.request.CreateOrderRequest;
import com.rig.orderservice.data.payload.response.GetOrderResponse;
import com.rig.orderservice.exception.NotFoundException;
import com.rig.orderservice.kafka.producer.KafkaProducer;
import com.rig.orderservice.repository.BookRepository;
import com.rig.orderservice.repository.CustomerAddressRepository;
import com.rig.orderservice.repository.CustomerRepository;
import com.rig.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final BookRepository bookRepository;

    private final CustomerRepository customerRepository;

    private final CustomerAddressRepository customerAddressRepository;

    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    private final KafkaProducer kafkaProducer;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = Cache.ORDER_CACHE, key = "#orderId")
    public GetOrderResponse getOrder(final long orderId) {
        return orderRepository.findById(orderId)
                .map(order -> modelMapper.map(order, GetOrderResponse.class))
                .orElseThrow(() -> new NotFoundException("Order not found with id: " + orderId));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = Cache.ORDER_LIST_CACHE, key = "#startDate.toString() + #endDate.toString()")
    public List<GetOrderResponse> getOrders(final LocalDate startDate, final LocalDate endDate) {
        return orderRepository.findAllBetween(startDate, endDate)
                .stream()
                .map(order -> modelMapper.map(order, GetOrderResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public synchronized void createOrder(final CreateOrderRequest createOrderRequest) {
        final Book book = getBook(createOrderRequest.getBookId());
        final Order order = saveOrder(createOrderRequest, book);
        final Book updatedBook = updateBookStock(book, createOrderRequest.getOrderQuantity());

        log.info("New order created with id: {}, order code: {}", order.getId(), order.getOrderCode());

        kafkaProducer.produce(order);
        kafkaProducer.produce(updatedBook);
    }

    private Order saveOrder(final CreateOrderRequest createOrderRequest, final Book book) {
        final Order order = new Order();
        order.setOrderCode(UUID.randomUUID());
        order.setOrderQuantity(createOrderRequest.getOrderQuantity());
        order.setOrderPrice(createOrderRequest.getOrderQuantity() * book.getPrice());
        order.setStatus(OrderStatus.IN_PROGRESS);
        order.setBook(book);
        order.setCustomer(getCustomer(createOrderRequest.getCustomerId()));
        order.setDeliveryAddress(getCustomerAddress(createOrderRequest.getDeliveryAddressId()));

        return orderRepository.saveAndFlush(order);
    }

    private Book getBook(final Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() ->
                new NotFoundException("Book not found with id: " + bookId)
        );
    }

    private Customer getCustomer(final Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() ->
                new NotFoundException("Customer not found with id: " + customerId)
        );
    }

    private CustomerAddress getCustomerAddress(final Long deliveryAddressId) {
        return customerAddressRepository.findById(deliveryAddressId).orElseThrow(() ->
                new NotFoundException("Customer address not found with id: " + deliveryAddressId)
        );
    }

    private Book updateBookStock(final Book book, final Integer orderQuantity) {
        book.setAvailableQuantity(book.getAvailableQuantity() - orderQuantity);
        book.setSoldQuantity(book.getSoldQuantity() + orderQuantity);

        return bookRepository.saveAndFlush(book);
    }

}
