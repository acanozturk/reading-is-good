package com.rig.apigateway.service.customer.client;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rig.apigateway.service.customer.config.CustomerServiceClientConfig;
import com.rig.apigateway.service.customer.request.CreateCustomerRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "${app.client.customer-service.name}",
        url = "${app.client.customer-service.url}",
        configuration = CustomerServiceClientConfig.class
)
public interface CustomerServiceClient {

    @GetMapping("/api/v1/customers/{customerId}/orders")
    @CircuitBreaker(name = "customer-service-circuit-breaker")
    @RateLimiter(name = "customer-service-rate-limiter")
    List<ObjectNode> getOrdersOfCustomer(@PathVariable final int customerId, final Pageable pageable);

    @PostMapping("/api/v1/customers")
    @CircuitBreaker(name = "customer-service-circuit-breaker")
    @RateLimiter(name = "customer-service-rate-limiter")
    void createCustomer(@Valid @RequestBody final CreateCustomerRequest createCustomerRequest);

}
