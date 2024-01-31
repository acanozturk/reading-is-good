package com.rig.apigateway.service.book.controller;

import com.rig.apigateway.service.book.client.BookServiceClient;
import com.rig.apigateway.service.book.request.CreateBookRequest;
import com.rig.apigateway.service.book.request.UpdateBookStockRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@AllArgsConstructor
@Tag(
        name = "Book Controller",
        description = "Responsible for book and book stock related operations."
)
@SecurityRequirement(name = "API Key")
@SecurityRequirement(name = "Bearer Authentication")
public final class BookController {

    private final BookServiceClient bookServiceClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new book.")
    public void createBook(@Valid @RequestBody final CreateBookRequest createBookRequest) {
        bookServiceClient.createBook(createBookRequest);
    }

    @PutMapping("/stock")
    @Operation(summary = "Updates the stock of a book.")
    public void updateBookStock(@Valid @RequestBody final UpdateBookStockRequest updateBookStockRequest) {
        bookServiceClient.updateBookStock(updateBookStockRequest);
    }

}
