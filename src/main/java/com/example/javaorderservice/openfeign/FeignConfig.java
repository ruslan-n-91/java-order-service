package com.example.javaorderservice.openfeign;
//
//import io.micrometer.tracing.propagation.Propagator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import io.micrometer.tracing.Tracer;
//import feign.RequestInterceptor;
//
//@Configuration
//public class FeignConfig {
//
//    @Bean
//    public RequestInterceptor tracingFeignRequestInterceptor(Tracer tracer, Propagator propagator) {
//        return template -> {
//            if (tracer.currentSpan() != null) {
//                // Автоматически добавляет W3C и B3
//                propagator.inject(tracer.currentSpan().context(),
//                        template,
//                        (request, key, value) -> request.header(key, value));
//            }
//        };
//    }
//}
