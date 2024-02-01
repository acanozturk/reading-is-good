package com.rig.customerservice.kafka.producer;

import com.rig.customerservice.MockedTest;
import com.rig.customerservice.data.constant.AccountStatus;
import com.rig.customerservice.data.constant.Gender;
import com.rig.customerservice.data.entity.Account;
import com.rig.customerservice.data.entity.Customer;
import com.rig.customerservice.data.entity.CustomerAddress;
import com.rig.customerservice.kafka.KafkaTopic;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;

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
    void testProduce() {
        final Account account = new Account();
        account.setId(1L);
        account.setStatus(AccountStatus.ACTIVE);
        
        final Customer customer = new Customer();
        customer.setId(1L);
        customer.setGender(Gender.FEMALE);
        customer.setDateOfBirth(LocalDate.now().minusYears(20));
        customer.setAccount(account);

        final CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setStreet("123 Main St");
        customerAddress.setCity("Anytown");
        customerAddress.setCountry("USA");

        kafkaProducer.produce(customer, customerAddress);

        verify(kafkaTemplate, times(1))
                .send(eq(KafkaTopic.CUSTOMER_HISTORY_TOPIC), eq("1"), anyString());
    }

}