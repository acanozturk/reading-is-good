package com.rig.apigateway.service.customer.config;

import com.rig.apigateway.service.customer.error.CustomerServiceClientErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class CustomerServiceClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomerServiceClientErrorDecoder();
    }
    
}
