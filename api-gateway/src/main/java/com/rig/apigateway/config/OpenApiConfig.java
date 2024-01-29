package com.rig.apigateway.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "API Key",
                                        new SecurityScheme()
                                                .bearerFormat("Api key")
                                                .name("Api Key Authentication")
                                                .scheme("api-key")
                                                .type(SecurityScheme.Type.HTTP)
                                )
                );
    }
}
