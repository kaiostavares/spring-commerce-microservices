package com.kaiostavares.apiGateway.factory;

import com.kaiostavares.apiGateway.enums.ServiceType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServicesConnectionParametersFactoryImpl implements ServicesConnectionParametersFactory {

    @Value("${services.products.url}")
    private String productServiceUrl;
    @Value("${services.products.path}")
    private String productsServicePath;

    @Value("${services.orders.url}")
    private String orderServiceUrl;
    @Value("${services.orders.path}")
    private String ordersServicePath;

    @Value("${services.inventory.url}")
    private String inventoryServiceUrl;
    @Value("${services.inventory.path}")
    private String inventoryServicePath;

    @Override
    public ConnectionParameters create(ServiceType serviceType) {
        return switch (serviceType) {
            case PRODUCT_SERVICE -> new ConnectionParameters(productsServicePath, productServiceUrl);
            case ORDER_SERVICE -> new ConnectionParameters(ordersServicePath, orderServiceUrl);
            case INVENTORY_SERVICE -> new ConnectionParameters(inventoryServicePath, inventoryServiceUrl);
        };
    }
}
