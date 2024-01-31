package com.rig.apigateway.service.authentication.config;

import com.rig.apigateway.service.authentication.error.AuthenticationServiceClientErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class AuthenticationServiceClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new AuthenticationServiceClientErrorDecoder();
    }
    
}
