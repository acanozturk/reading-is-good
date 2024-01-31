package com.rig.orderservice.logger;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public final class HttpRequestResponseLogger extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final Instant start = Instant.now();
        final ContentCachingRequestWrapper cachedRequest = new ContentCachingRequestWrapper(request);
        final ContentCachingResponseWrapper cachedResponse = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(cachedRequest, cachedResponse);

        final Instant end = Instant.now();

        log.info(
                "Request received. Method: {}, Url: {}, Body: {}, IP: {}, Headers: {}",
                cachedRequest.getMethod(),
                cachedRequest.getRequestURI(),
                new String(cachedRequest.getContentAsByteArray()),
                cachedRequest.getRemoteAddr(),
                getHeadersInfo(cachedRequest)
        );

        log.info(
                "Response created. Status: {}, Body: {}, Elapsed Time: {} ms",
                cachedResponse.getStatus(),
                new String(cachedResponse.getContentAsByteArray()),
                Duration.between(start, end).toMillis()
        );

        cachedResponse.copyBodyToResponse();
    }

    private Map<String, String> getHeadersInfo(final HttpServletRequest request) {
        final Map<String, String> headerMap = new HashMap<>();
        final Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            final String key = headerNames.nextElement();
            final String value = request.getHeader(key);

            headerMap.put(key, value);
        }

        return headerMap;
    }

}
