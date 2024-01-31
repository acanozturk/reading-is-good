package com.rig.bookservice.service;

import com.rig.bookservice.data.entity.Book;
import com.rig.bookservice.data.payload.request.CreateBookRequest;
import com.rig.bookservice.data.payload.request.UpdateBookStockRequest;
import com.rig.bookservice.exception.NotFoundException;
import com.rig.bookservice.kafka.producer.KafkaProducer;
import com.rig.bookservice.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final ModelMapper modelMapper;
    
    private final KafkaProducer kafkaProducer;

    @Override
    public void createBook(final CreateBookRequest createBookRequest) {
        final Book book = modelMapper.map(createBookRequest, Book.class);
        book.setAvailableQuantity(createBookRequest.getTotalQuantity());
        book.setSoldQuantity(0);

        bookRepository.saveAndFlush(book);

        log.info("New book created with id: {}", book.getId());
        
        kafkaProducer.produce(book);
    }

    @Override
    public synchronized void updateBookStock(final UpdateBookStockRequest updateBookStockRequest) {
        final Long bookId = updateBookStockRequest.getBookId();
        final Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new NotFoundException("Book not found with id: " + bookId)
        );

        final Integer oldTotalQuantity = book.getTotalQuantity();
        final Integer newTotalQuantity = updateBookStockRequest.getTotalQuantity();
        final Integer oldAvailableQuantity = book.getAvailableQuantity();
        final Integer newAvailableQuantity = calculateNewAvailableQuantity(
                oldAvailableQuantity,
                newTotalQuantity - oldTotalQuantity
        );

        book.setTotalQuantity(newTotalQuantity);
        book.setAvailableQuantity(newAvailableQuantity);

        final Book updatedBook = bookRepository.saveAndFlush(book);

        log.info("Book stock updated for book id: {}", book.getId());

        kafkaProducer.produce(updatedBook);
    }

    private Integer calculateNewAvailableQuantity(final Integer totalQuantityDifference, final Integer oldAvailableQuantity) {
        return totalQuantityDifference > 0
                ? oldAvailableQuantity + totalQuantityDifference
                : oldAvailableQuantity;
    }

}
