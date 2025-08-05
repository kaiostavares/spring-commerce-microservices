package com.kaiostavares.orderservice.repositories;

import com.kaiostavares.orderservice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
