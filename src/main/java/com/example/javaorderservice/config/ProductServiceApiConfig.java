package com.example.javaorderservice.config;

//import com.example.client.ApiClient;
//import com.example.client.api.ProductControllerApi;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestClient;
//
//@Configuration
//public class ProductServiceApiConfig {
//
//    @Value("${product.service.url}")
//    private String productServiceUrl;
//
//    @Bean
//    public ProductControllerApi productServiceApiClient(RestClient.Builder restClient) {
//        ApiClient apiClient = new ApiClient(restClient.baseUrl(productServiceUrl).build());
//        //apiClient.setBasePath(productServiceUrl);
//        return new ProductControllerApi(apiClient);
//    }
//}
