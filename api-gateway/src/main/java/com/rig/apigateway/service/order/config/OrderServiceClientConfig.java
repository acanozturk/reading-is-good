package com.rig.apigateway.service.order.config;

import com.rig.apigateway.service.order.error.OrderServiceClientErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class OrderServiceClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new OrderServiceClientErrorDecoder();
    }
    
}
