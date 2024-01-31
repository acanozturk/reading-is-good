package com.rig.apigateway.service.order.client;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rig.apigateway.service.order.config.OrderServiceClientConfig;
import com.rig.apigateway.service.order.request.CreateOrderRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@FeignClient(
        name = "${app.client.order-service.name}",
        url = "${app.client.order-service.url}",
        configuration = OrderServiceClientConfig.class
)
public interface OrderServiceClient {

    @GetMapping(value = "/api/v1/orders")
    @CircuitBreaker(name = "order-service-circuit-breaker")
    @RateLimiter(name = "order-service-rate-limiter")
    List<ObjectNode> getOrders(@RequestParam final LocalDate startDate, @RequestParam final LocalDate endDate);

    @GetMapping(value = "/api/v1/orders/{orderId}")
    @CircuitBreaker(name = "order-service-circuit-breaker")
    @RateLimiter(name = "order-service-rate-limiter")
    ObjectNode getOrder(@PathVariable final long orderId);

    @PostMapping(value = "/api/v1/orders")
    @CircuitBreaker(name = "order-service-circuit-breaker")
    @RateLimiter(name = "order-service-rate-limiter")
    void createOrder(@Valid @RequestBody final CreateOrderRequest createOrderRequest);

}
