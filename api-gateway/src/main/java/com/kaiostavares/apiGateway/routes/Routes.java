package com.kaiostavares.apiGateway.routes;

import com.kaiostavares.apiGateway.enums.ServiceType;
import com.kaiostavares.apiGateway.factory.ServicesConnectionParametersFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.*;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class Routes {

    private final ServicesConnectionParametersFactory connectionFactory;

    @Bean
    public RouterFunction<ServerResponse> productServiceRoute(){
        final var connectionParameters = connectionFactory.create(ServiceType.PRODUCT_SERVICE);
        return createRoute(
                ServiceType.PRODUCT_SERVICE.name().toLowerCase(),
                connectionParameters.url(),
                connectionParameters.path()
        );
    }

    @Bean
    public RouterFunction<ServerResponse> ordersServiceRoute(){
        final var connectionParameters = connectionFactory.create(ServiceType.ORDER_SERVICE);
        return createRoute(
                ServiceType.ORDER_SERVICE.name().toLowerCase(),
                connectionParameters.url(),
                connectionParameters.path()
        );
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceRoute(){
        final var connectionParameters = connectionFactory.create(ServiceType.INVENTORY_SERVICE);
        return createRoute(
                ServiceType.INVENTORY_SERVICE.name().toLowerCase(),
                connectionParameters.url(),
                connectionParameters.path()
        );
    }

    private static RouterFunction<ServerResponse> createRoute(String routeId, String url, String path) {
       return GatewayRouterFunctions.route(routeId)
               .route(RequestPredicates.path(path), HandlerFunctions.http(url))
               .build();
    }
}
