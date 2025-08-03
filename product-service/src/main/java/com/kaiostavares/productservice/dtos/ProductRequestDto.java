package com.kaiostavares.productservice.dtos;

import java.math.BigDecimal;

public record ProductRequestDto(
        String name,
        String description,
        BigDecimal price
) {
}
