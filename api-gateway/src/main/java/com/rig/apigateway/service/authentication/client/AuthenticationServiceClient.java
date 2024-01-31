package com.rig.apigateway.service.authentication.client;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rig.apigateway.service.authentication.config.AuthenticationServiceClientConfig;
import com.rig.apigateway.service.authentication.request.LoginRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "${app.client.authentication-service.name}",
        url = "${app.client.authentication-service.url}",
        configuration = AuthenticationServiceClientConfig.class
)
public interface AuthenticationServiceClient {

    @PostMapping(value = "/api/v1/auth/login")
    @CircuitBreaker(name = "authentication-service-circuit-breaker")
    @RateLimiter(name = "authentication-service-rate-limiter")
    ObjectNode login(@RequestBody final LoginRequest loginRequest);
    
}
