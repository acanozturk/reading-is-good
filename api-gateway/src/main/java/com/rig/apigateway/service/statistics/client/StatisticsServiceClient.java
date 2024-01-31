package com.rig.apigateway.service.statistics.client;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rig.apigateway.service.statistics.config.StatisticsServiceClientConfig;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "${app.client.statistics-service.name}",
        url = "${app.client.statistics-service.url}",
        configuration = StatisticsServiceClientConfig.class
)
public interface StatisticsServiceClient {

    @GetMapping("/api/v1/statistics/{customerId}")
    @CircuitBreaker(name = "statistics-service-circuit-breaker")
    @RateLimiter(name = "statistics-service-rate-limiter")
    List<ObjectNode> getMonthlyOrderStatisticsOfCustomer(@PathVariable final int customerId);
    
}
