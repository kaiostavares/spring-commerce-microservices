package com.kaiostavares.productservice.dtos;

import java.math.BigDecimal;

public record ProductResponseDto(
    String id,
    String name,
    String description,
    BigDecimal price
) {
}
