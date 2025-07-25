package com.example.javaorderservice.httpexchange;

import brave.Tracer;
import brave.propagation.TraceContext;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TracingClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private final Tracer tracer;

    public TracingClientHttpRequestInterceptor(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        TraceContext traceContext = tracer.currentSpan() != null ? tracer.currentSpan().context() : null;

        if (traceContext != null) {
            // Добавляем только не-null заголовки
            addHeaderIfNotNull(request, "X-B3-TraceId", traceContext.traceIdString());
            addHeaderIfNotNull(request, "X-B3-SpanId", traceContext.spanIdString());

            // ParentSpanId может быть null для корневого span
            if (traceContext.parentIdString() != null) {
                addHeaderIfNotNull(request, "X-B3-ParentSpanId", traceContext.parentIdString());
            }

            addHeaderIfNotNull(request, "X-B3-Sampled", "1");
        }

        return execution.execute(request, body);
    }

    private void addHeaderIfNotNull(HttpRequest request, String headerName, String headerValue) {
        if (headerValue != null) {
            request.getHeaders().add(headerName, headerValue);
        }
    }
}
