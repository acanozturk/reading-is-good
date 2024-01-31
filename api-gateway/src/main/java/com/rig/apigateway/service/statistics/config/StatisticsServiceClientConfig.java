package com.rig.apigateway.service.statistics.config;

import com.rig.apigateway.service.statistics.error.StatisticsServiceClientErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class StatisticsServiceClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new StatisticsServiceClientErrorDecoder();
    }
    
}
