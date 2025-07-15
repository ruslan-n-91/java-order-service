package com.example.javaorderservice.httpexchange;

import java.time.Instant;

public record GetProductResponseDto(
        Long id,
        String productName,
        String description,
        Integer quantity,
        Instant createdAt
) {
}
