package com.rig.bookservice.controller;

import com.rig.bookservice.data.entity.Book;
import com.rig.bookservice.data.payload.request.CreateBookRequest;
import com.rig.bookservice.data.payload.request.UpdateBookStockRequest;
import com.rig.bookservice.repository.BookRepository;
import com.rig.bookservice.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends ControllerTest {

    @MockBean
    private BookService bookService;
    
    @MockBean
    private BookRepository bookRepository;

    @Test
    void testCreateBook() throws Exception {
        final CreateBookRequest createBookRequest = new CreateBookRequest();
        createBookRequest.setTitle("Title");
        createBookRequest.setGenre("Genre");
        createBookRequest.setAuthor("Author");
        createBookRequest.setPublishDate(LocalDate.now().minusYears(1));
        createBookRequest.setPages(100);
        createBookRequest.setPrice(100.0);
        createBookRequest.setTotalQuantity(100);

        mockMvc.perform(
                        post("/api/v1/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getAsJson(createBookRequest))
                )
                .andExpect(status().isCreated());

        verify(bookService, times(1)).createBook(any(CreateBookRequest.class));
    }

    @Test
    void testCreateBook_InvalidRequestPayload() throws Exception {
        final CreateBookRequest createBookRequest = new CreateBookRequest();
        createBookRequest.setTitle("Title");
        createBookRequest.setGenre("Genre");
        createBookRequest.setAuthor("Author");
        createBookRequest.setPublishDate(LocalDate.now().minusYears(1));
        createBookRequest.setPages(100);
        createBookRequest.setPrice(-3.0);
        createBookRequest.setTotalQuantity(100);

        mockMvc.perform(
                        post("/api/v1/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getAsJson(createBookRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(BindException.class));

        verifyNoInteractions(bookService);
    }

    @Test
    void testUpdateBookStock() throws Exception {
        final Book book = new Book();
        book.setAvailableQuantity(100);
        
        final UpdateBookStockRequest updateBookStockRequest = new UpdateBookStockRequest();
        updateBookStockRequest.setBookId(1L);
        updateBookStockRequest.setTotalQuantity(150);

        when(bookRepository.existsById(anyLong())).thenReturn(true);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        
        mockMvc.perform(
                        put("/api/v1/books/stock")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getAsJson(updateBookStockRequest))
                )
                .andExpect(status().isOk());

        verify(bookService, times(1)).updateBookStock(any(UpdateBookStockRequest.class));
    }

    @Test
    void testUpdateBookStock_BookNotFound() throws Exception {
        final UpdateBookStockRequest updateBookStockRequest = new UpdateBookStockRequest();
        updateBookStockRequest.setBookId(1L);
        updateBookStockRequest.setTotalQuantity(150);

        when(bookRepository.existsById(anyLong())).thenReturn(false);

        mockMvc.perform(
                        put("/api/v1/books/stock")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getAsJson(updateBookStockRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(BindException.class));

        verifyNoInteractions(bookService);
    }
    
    @Test
    void testUpdateBookStock_InvalidQuantity() throws Exception {
        final Book book = new Book();
        book.setAvailableQuantity(100);

        final UpdateBookStockRequest updateBookStockRequest = new UpdateBookStockRequest();
        updateBookStockRequest.setBookId(1L);
        updateBookStockRequest.setTotalQuantity(50);

        when(bookRepository.existsById(anyLong())).thenReturn(true);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        mockMvc.perform(
                        put("/api/v1/books/stock")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getAsJson(updateBookStockRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(BindException.class));

        verifyNoInteractions(bookService);
    }

}