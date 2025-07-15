package com.example.javaorderservice.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service", url = "http://localhost:8256")
public interface ProductServiceClient {

    @GetMapping("api/v1/products")
    ResponseEntity<List<GetProductResponseDto>> getAllProducts();

    @GetMapping("api/v1/products/{productId}")
    ResponseEntity<GetProductResponseDto> getProductById(@PathVariable("productId") Long productId);

    @PostMapping("api/v1/products")
    ResponseEntity<CreateProductResponseDto> createProduct(@RequestBody CreateProductRequestDto requestDto);
}
