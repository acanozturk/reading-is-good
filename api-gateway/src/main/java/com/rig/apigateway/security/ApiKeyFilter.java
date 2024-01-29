package com.rig.apigateway.security;

import com.rig.apigateway.exception.ApiKeyException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

import static com.rig.apigateway.config.WebSecurityConfig.PUBLIC_WHITELIST;

@Component
@RequiredArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

    private final MessageSource messageSource;

    @Value("${app.security.api-key.header}")
    private String apiKeyHeader;

    @Value("${app.security.api-key.value}")
    private String apiKeyValue;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!apiKeyValue.equals(request.getHeader(apiKeyHeader))) {
            throw new ApiKeyException(messageSource.getMessage("exception.api-key", null, Locale.getDefault()));
        }
        
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return Arrays.stream(PUBLIC_WHITELIST).anyMatch(url -> request.getRequestURI().startsWith(url));
    }
    
}
