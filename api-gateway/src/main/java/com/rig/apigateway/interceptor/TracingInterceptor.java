package com.rig.apigateway.interceptor;

import com.rig.apigateway.data.constant.TracingConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public final class TracingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        setCorrelationId(request);
        setExecutorService(request);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.clear();
    }

    private void setCorrelationId(final HttpServletRequest request) {
        final String correlationId = UUID.randomUUID().toString();

        request.setAttribute(TracingConstant.X_CORRELATION_ID, correlationId);

        MDC.put(TracingConstant.X_CORRELATION_ID, correlationId);
    }

    private void setExecutorService(final HttpServletRequest request) {
        final String executorService = "api-gateway";

        request.setAttribute(TracingConstant.X_EXECUTOR_SERVICE, executorService);

        MDC.put(TracingConstant.X_EXECUTOR_SERVICE, executorService);
    }

}
