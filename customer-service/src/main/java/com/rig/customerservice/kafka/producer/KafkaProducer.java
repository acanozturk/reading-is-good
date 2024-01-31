package com.rig.customerservice.kafka.producer;

import com.google.gson.Gson;
import com.rig.customerservice.data.entity.Customer;
import com.rig.customerservice.data.entity.CustomerAddress;
import com.rig.customerservice.kafka.KafkaTopic;
import com.rig.customerservice.kafka.event.CustomerHistoryEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
@Async
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    
    public void produce(final Customer customer, final CustomerAddress customerAddress) {
        final CustomerHistoryEvent customerHistoryEvent = new CustomerHistoryEvent().from(customer, customerAddress);
        final String eventJson = new Gson().toJson(customerHistoryEvent);

        kafkaTemplate.send(
                KafkaTopic.CUSTOMER_HISTORY_TOPIC,
                String.valueOf(customerHistoryEvent.getCustomerId()),
                eventJson
        );

        log.info("Customer history event produced: {}", eventJson);
    }

}
