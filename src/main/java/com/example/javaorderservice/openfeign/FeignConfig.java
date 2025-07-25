package com.example.javaorderservice.openfeign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.micrometer.tracing.Tracer;
import feign.RequestInterceptor;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor tracingFeignRequestInterceptor(Tracer tracer) {
        return template -> {
            if (tracer.currentSpan() != null) {
                template.header("X-B3-TraceId", tracer.currentSpan().context().traceId());
                template.header("X-B3-SpanId", tracer.currentSpan().context().spanId());
                if (tracer.currentSpan().context().parentId() != null) {
                    template.header("X-B3-ParentSpanId", tracer.currentSpan().context().parentId());
                }
            }
        };
    }
}
