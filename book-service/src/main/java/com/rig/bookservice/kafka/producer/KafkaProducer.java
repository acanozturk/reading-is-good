package com.rig.bookservice.kafka.producer;

import com.google.gson.Gson;
import com.rig.bookservice.data.entity.Book;
import com.rig.bookservice.kafka.KafkaTopic;
import com.rig.bookservice.kafka.event.BookHistoryEvent;
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
