package com.rig.apigateway.service.book.config;

import com.rig.apigateway.service.book.error.BookServiceClientErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class BookServiceClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new BookServiceClientErrorDecoder();
    }
    
}
