package com.rig.apigateway.service.book.client;

import com.rig.apigateway.service.book.config.BookServiceClientConfig;
import com.rig.apigateway.service.book.request.CreateBookRequest;
import com.rig.apigateway.service.book.request.UpdateBookStockRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "${app.client.book-service.name}",
        url = "${app.client.book-service.url}",
        configuration = BookServiceClientConfig.class
)
public interface BookServiceClient {

    @PostMapping(value = "/api/v1/books")
    @CircuitBreaker(name = "book-service-circuit-breaker")
    @RateLimiter(name = "book-service-rate-limiter")
    void createBook(@RequestBody final CreateBookRequest createBookRequest);

    @PostMapping(value = "/api/v1/books/stock")
    @CircuitBreaker(name = "book-service-circuit-breaker")
    @RateLimiter(name = "book-service-rate-limiter")
    void updateBookStock(@RequestBody final UpdateBookStockRequest updateBookStockRequest);
    
}
