package com.kaiostavares.orderservice.services;

import com.kaiostavares.orderservice.dtos.OrderRequest;
import com.kaiostavares.orderservice.models.Order;
import com.kaiostavares.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    public void placeOrder(OrderRequest orderRequest) {
        final Order order = new Order(
                UUID.randomUUID().toString(),
                orderRequest.skuCode(),
                orderRequest.price(),
                orderRequest.quantity()
        );
        orderRepository.save(order);
    }
}
