package com.rig.apigateway.client.authentication;

import com.rig.apigateway.client.authentication.config.AuthenticationServiceClientConfig;
import com.rig.apigateway.client.authentication.request.LoginRequest;
import com.rig.apigateway.client.authentication.response.JwtResponse;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
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
    @RateLimiter(name = "authentication-service-rate-limiter")
    JwtResponse login(@Valid @RequestBody LoginRequest loginRequest);
    
}
