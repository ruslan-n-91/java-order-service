package com.example.javaorderservice.httpexchange;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange(url = "http://localhost:8256")
public interface ProductServiceClient {

    @GetExchange("api/v1/products")
    ResponseEntity<List<GetProductResponseDto>> getAllProducts();

    @GetExchange("api/v1/products/{productId}")
    ResponseEntity<GetProductResponseDto> getProductById(@PathVariable("productId") Long productId);

    @PostExchange("api/v1/products")
    ResponseEntity<CreateProductResponseDto> createProduct(@RequestBody CreateProductRequestDto requestDto);
}
