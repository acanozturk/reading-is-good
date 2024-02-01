package com.rig.orderservice.service;

import com.rig.orderservice.MockedTest;
import com.rig.orderservice.data.constant.AccountStatus;
import com.rig.orderservice.data.constant.Gender;
import com.rig.orderservice.data.constant.OrderStatus;
import com.rig.orderservice.data.entity.*;
import com.rig.orderservice.data.payload.request.CreateOrderRequest;
import com.rig.orderservice.data.payload.response.GetOrderResponse;
import com.rig.orderservice.kafka.producer.KafkaProducer;
import com.rig.orderservice.repository.BookRepository;
import com.rig.orderservice.repository.CustomerAddressRepository;
import com.rig.orderservice.repository.CustomerRepository;
import com.rig.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class OrderServiceImplTest extends MockedTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerAddressRepository customerAddressRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void testGetOrder() {
        final long orderId = 1L;
        final Order order = new Order();
        final GetOrderResponse getOrderResponse = new GetOrderResponse();
        
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(modelMapper.map(order, GetOrderResponse.class)).thenReturn(getOrderResponse);

        orderService.getOrder(orderId);

        verify(orderRepository, times(1)).findById(orderId);
        verify(modelMapper, times(1)).map(order, GetOrderResponse.class);
    }

    @Test
    void testGetOrders() {
        final LocalDate startDate = LocalDate.now().minusDays(7);
        final LocalDate endDate = LocalDate.now();
        final List<Order> orders = new ArrayList<>();

        when(orderRepository.findAllBetween(startDate, endDate)).thenReturn(orders);
        when(modelMapper.map(any(), eq(GetOrderResponse.class))).thenReturn(new GetOrderResponse());

        orderService.getOrders(startDate, endDate);

        verify(orderRepository, times(1)).findAllBetween(startDate, endDate);
        verify(modelMapper, times(orders.size())).map(any(), eq(GetOrderResponse.class));
    }

    @Test
    void testCreateOrder() {
        final CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setBookId(1L);
        createOrderRequest.setCustomerId(2L);
        createOrderRequest.setDeliveryAddressId(3L);
        createOrderRequest.setOrderQuantity(1);
        
        final Book book = new Book();
        book.setId(1L);
        book.setAvailableQuantity(100);
        book.setTotalQuantity(100);
        book.setSoldQuantity(0);
        book.setPublishDate(LocalDate.now().minusYears(10));
        book.setPrice(100.0);

        final Account account = new Account();
        account.setId(1L);
        account.setStatus(AccountStatus.ACTIVE);

        final CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setId(1L);
        
        final Customer customer = new Customer();
        customer.setId(1L);
        customer.setGender(Gender.MALE);
        customer.setDateOfBirth(LocalDate.now().minusYears(20));
        customer.setAccount(account);
        customer.setAddresses(List.of(customerAddress));
        
        final Order order = new Order();
        order.setId(1L);
        order.setOrderCode(UUID.randomUUID());
        order.setStatus(OrderStatus.IN_PROGRESS);
        order.setBook(book);
        order.setCustomer(customer);
        order.setDeliveryAddress(customerAddress);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(customerRepository.findById(2L)).thenReturn(Optional.of(customer));
        when(customerAddressRepository.findById(3L)).thenReturn(Optional.of(customerAddress));
        when(orderRepository.saveAndFlush(any())).thenReturn(order);
        when(bookRepository.saveAndFlush(any())).thenReturn(book);

        orderService.createOrder(createOrderRequest);

        verify(bookRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).findById(2L);
        verify(customerAddressRepository, times(1)).findById(3L);
        verify(orderRepository, times(1)).saveAndFlush(any());
        verify(kafkaProducer, times(1)).produceOrderEvent(any());
        verify(kafkaProducer, times(1)).produceBookEvent(any());
    }
    
}