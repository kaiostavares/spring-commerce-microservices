package com.kaiostavares.orderservice.dtos;

import java.math.BigDecimal;

public record OrderRequest(String skuCode, BigDecimal price, Integer quantity) {
}