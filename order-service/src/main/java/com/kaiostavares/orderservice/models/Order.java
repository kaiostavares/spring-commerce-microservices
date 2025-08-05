package com.kaiostavares.orderservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;

    public Order(String orderNumber, String skuCode, BigDecimal price, Integer quantity) {
        this.orderNumber = orderNumber;
        this.skuCode = skuCode;
        this.price = price;
        this.quantity = quantity;
    }
}
