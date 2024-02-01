package com.rig.orderservice.kafka.producer;

import com.rig.orderservice.MockedTest;
import com.rig.orderservice.data.constant.AccountStatus;
import com.rig.orderservice.data.constant.Gender;
import com.rig.orderservice.data.constant.OrderStatus;
import com.rig.orderservice.data.entity.*;
import com.rig.orderservice.kafka.KafkaTopic;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class KafkaProducerTest extends MockedTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducer kafkaProducer;

    @Test
    void testProduceOrderEvent() {
        final Order order = buildOrder();

        kafkaProducer.produceOrderEvent(order);

        verify(kafkaTemplate, times(1))
                .send(eq(KafkaTopic.ORDER_HISTORY_TOPIC), eq("1"), anyString());
    }

    @Test
    void testProduceBookEvent() {
        final Book book = new Book();
        book.setId(1L);
        book.setPublishDate(LocalDate.now().minusYears(10));

        kafkaProducer.produceBookEvent(book);

        verify(kafkaTemplate, times(1))
                .send(eq(KafkaTopic.BOOK_HISTORY_TOPIC), eq("1"), anyString());
    }
    
    private Order buildOrder() {
        final Book book = new Book();
        book.setId(1L);
        book.setPublishDate(LocalDate.now().minusYears(10));

        final Account account = new Account();
        account.setId(1L);
        account.setStatus(AccountStatus.ACTIVE);

        final CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setId(1L);

        final Customer customer = new Customer();
        customer.setId(1L);
        customer.setGender(Gender.FEMALE);
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
        
        return order;
    }

}
