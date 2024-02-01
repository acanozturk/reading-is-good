package com.rig.bookservice.kafka.producer;

import com.rig.bookservice.MockedTest;
import com.rig.bookservice.data.entity.Book;
import com.rig.bookservice.kafka.KafkaTopic;
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
        final Book book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        book.setPublishDate(LocalDate.now().minusYears(1));

        kafkaProducer.produce(book);

        verify(kafkaTemplate, times(1))
                .send(eq(KafkaTopic.BOOK_HISTORY_TOPIC), eq("1"), anyString());
    }

}