package com.example.javaorderservice.openfeign;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GetProductResponseDto implements Serializable {
    private Long id;
    private String productName;
    private String description;
    private Integer quantity;
    private Instant createdAt;
}
