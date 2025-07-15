package com.example.javaorderservice.httpexchange;

import java.time.Instant;

public record CreateProductResponseDto(
        Long id,
        String productName,
        String description,
        Integer quantity,
        Instant createdAt
) {
}
