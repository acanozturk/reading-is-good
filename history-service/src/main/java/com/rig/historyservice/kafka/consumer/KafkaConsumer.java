package com.rig.historyservice.kafka.consumer;

import com.google.gson.Gson;
import com.rig.historyservice.data.document.BookHistory;
import com.rig.historyservice.data.document.CustomerHistory;
import com.rig.historyservice.data.document.OrderHistory;
import com.rig.historyservice.kafka.KafkaConsumerGroup;
import com.rig.historyservice.kafka.KafkaTopic;
import com.rig.historyservice.kafka.event.history.BookHistoryEvent;
import com.rig.historyservice.kafka.event.history.CustomerHistoryEvent;
import com.rig.historyservice.kafka.event.history.OrderHistoryEvent;
import com.rig.historyservice.repository.BookHistoryRepository;
import com.rig.historyservice.repository.CustomerHistoryRepository;
import com.rig.historyservice.repository.OrderHistoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final BookHistoryRepository bookHistoryRepository;

    private final CustomerHistoryRepository customerHistoryRepository;

    private final OrderHistoryRepository orderHistoryRepository;

    private final ModelMapper modelMapper;

    @KafkaListener(
            topics = KafkaTopic.BOOK_HISTORY_TOPIC,
            groupId = KafkaConsumerGroup.BOOK_HISTORY_CG
    )
    public void consumeBookHistory(final String event) {
        log.info("Book history event received: {}", event);

        final BookHistoryEvent bookHistoryEvent = new Gson().fromJson(event, BookHistoryEvent.class);
        final BookHistory bookHistory = modelMapper.map(bookHistoryEvent, BookHistory.class);
        final BookHistory savedBookHistory = bookHistoryRepository.save(bookHistory);

        log.info("Book history event consumed: {}", savedBookHistory);
    }

    @KafkaListener(
            topics = KafkaTopic.CUSTOMER_HISTORY_TOPIC,
            groupId = KafkaConsumerGroup.CUSTOMER_HISTORY_CG
    )
    public void consumeCustomerHistory(final String event) {
        log.info("Customer history event received: {}", event);

        final CustomerHistoryEvent customerHistoryEvent = new Gson().fromJson(event, CustomerHistoryEvent.class);
        final CustomerHistory customerHistory = modelMapper.map(customerHistoryEvent, CustomerHistory.class);
        final CustomerHistory savedCustomerHistory = customerHistoryRepository.save(customerHistory);

        log.info("Customer history event consumed: {}", savedCustomerHistory);
    }

    @KafkaListener(
            topics = KafkaTopic.ORDER_HISTORY_TOPIC,
            groupId = KafkaConsumerGroup.ORDER_HISTORY_CG
    )
    public void consumeOrderHistory(final String event) {
        log.info("Order history event received: {}", event);

        final OrderHistoryEvent orderHistoryEvent = new Gson().fromJson(event, OrderHistoryEvent.class);
        final OrderHistory orderHistory = modelMapper.map(orderHistoryEvent, OrderHistory.class);
        final OrderHistory savedOrderHistory = orderHistoryRepository.save(orderHistory);

        log.info("Order history event consumed: {}", savedOrderHistory);
    }

}
