package com.example.javaorderservice.httpexchange;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ProductServiceClientConfig {

    @Bean
    public ProductServiceClient productServiceClient(TracingClientHttpRequestInterceptor tracingInterceptor) {
        RestClient restClient = RestClient.builder()
                //.baseUrl("http://localhost:8256")  // Можно переопределить URL
                .requestInterceptor(tracingInterceptor)
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(ProductServiceClient.class);
    }
}
