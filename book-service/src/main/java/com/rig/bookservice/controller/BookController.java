package com.rig.bookservice.controller;

import com.rig.bookservice.data.payload.request.CreateBookRequest;
import com.rig.bookservice.data.payload.request.UpdateBookStockRequest;
import com.rig.bookservice.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@AllArgsConstructor
public final class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBook(@Valid @RequestBody final CreateBookRequest createBookRequest) {
        bookService.createBook(createBookRequest);
    }

    @PutMapping("/stock")
    public void updateBookStock(@Valid @RequestBody final UpdateBookStockRequest updateBookStockRequest) {
        bookService.updateBookStock(updateBookStockRequest);
    }

}
