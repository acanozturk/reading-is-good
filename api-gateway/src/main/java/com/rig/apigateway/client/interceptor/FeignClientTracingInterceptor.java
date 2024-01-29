package com.rig.apigateway.client.interceptor;

import com.rig.apigateway.data.constant.TracingConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public final class FeignClientTracingInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        setCorrelationId(requestTemplate);
        setExecutorService(requestTemplate);
    }

    private void setCorrelationId(final RequestTemplate requestTemplate) {
        String correlationId = MDC.get(TracingConstant.X_CORRELATION_ID);

        if (StringUtils.isEmpty(correlationId)) {
            correlationId = UUID.randomUUID().toString();
        }

        requestTemplate.header(TracingConstant.X_CORRELATION_ID, correlationId);
    }

    private void setExecutorService(final RequestTemplate requestTemplate) {
        String executorService = MDC.get(TracingConstant.X_EXECUTOR_SERVICE);

        if (StringUtils.isEmpty(executorService)) {
            executorService = "api-gateway";
        }

        requestTemplate.header(TracingConstant.X_EXECUTOR_SERVICE, executorService);
    }

}
