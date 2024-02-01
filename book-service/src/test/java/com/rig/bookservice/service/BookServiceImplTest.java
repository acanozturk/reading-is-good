package com.rig.bookservice.service;

import com.rig.bookservice.MockedTest;
import com.rig.bookservice.data.entity.Book;
import com.rig.bookservice.data.payload.request.CreateBookRequest;
import com.rig.bookservice.data.payload.request.UpdateBookStockRequest;
import com.rig.bookservice.kafka.producer.KafkaProducer;
import com.rig.bookservice.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.Mockito.*;

class BookServiceImplTest extends MockedTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void testCreateBook() {
        final CreateBookRequest createBookRequest = new CreateBookRequest();
        final Book book = new Book();
        book.setId(1L);

        when(modelMapper.map(createBookRequest, Book.class)).thenReturn(book);

        bookService.createBook(createBookRequest);

        verify(bookRepository, times(1)).saveAndFlush(book);
        verify(kafkaProducer, times(1)).produce(book);
    }

    @Test
    void testUpdateBookStock() {
        final UpdateBookStockRequest updateBookStockRequest = new UpdateBookStockRequest();
        updateBookStockRequest.setBookId(1L);
        updateBookStockRequest.setTotalQuantity(10);

        final Book book = new Book();
        book.setId(1L);
        book.setTotalQuantity(5);
        book.setAvailableQuantity(5);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.saveAndFlush(any(Book.class))).thenReturn(book);

        bookService.updateBookStock(updateBookStockRequest);

        verify(bookRepository, times(1)).saveAndFlush(book);
        verify(kafkaProducer, times(1)).produce(book);
    }

}