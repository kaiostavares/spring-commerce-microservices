package com.kaiostavares.orderservice.services;

import com.kaiostavares.orderservice.clients.InventoryClient;
import com.kaiostavares.orderservice.dtos.OrderRequest;
import com.kaiostavares.orderservice.exceptions.OutOfStockException;
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
    private final InventoryClient inventoryClient;

    @Transactional
    public void placeOrder(OrderRequest orderRequest) {
        final boolean inStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if(!inStock){
            throw new OutOfStockException("Product is out of stock");
        }
        final Order order = new Order(
                UUID.randomUUID().toString(),
                orderRequest.skuCode(),
                orderRequest.price(),
                orderRequest.quantity()
        );
        orderRepository.save(order);
    }
}
