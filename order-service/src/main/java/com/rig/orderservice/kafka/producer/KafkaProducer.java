package com.rig.orderservice.kafka.producer;

import com.google.gson.Gson;
import com.rig.orderservice.data.entity.Book;
import com.rig.orderservice.data.entity.Order;
import com.rig.orderservice.kafka.KafkaTopic;
import com.rig.orderservice.kafka.event.BookHistoryEvent;
import com.rig.orderservice.kafka.event.OrderHistoryEvent;
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

    public void produce(final Order order) {
        final OrderHistoryEvent orderHistoryEvent = new OrderHistoryEvent().from(order);
        final String eventJson = new Gson().toJson(orderHistoryEvent);

        kafkaTemplate.send(
                KafkaTopic.ORDER_HISTORY_TOPIC,
                String.valueOf(orderHistoryEvent.getOrderId()),
                eventJson
        );

        log.info("Order history event produced: {}", eventJson);
    }

    public void produce(final Book book) {
        final BookHistoryEvent bookHistoryEvent = new BookHistoryEvent().from(book);
        final String eventJson = new Gson().toJson(bookHistoryEvent);

        kafkaTemplate.send(
                KafkaTopic.BOOK_HISTORY_TOPIC,
                String.valueOf(bookHistoryEvent.getBookId()),
                eventJson
        );

        log.info("Book history event produced: {}", eventJson);
    }

}
