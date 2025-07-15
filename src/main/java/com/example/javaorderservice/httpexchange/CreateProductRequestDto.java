package com.example.javaorderservice.httpexchange;

public record CreateProductRequestDto(
        Long id,
        String productName,
        String description,
        Integer quantity
) {
}
