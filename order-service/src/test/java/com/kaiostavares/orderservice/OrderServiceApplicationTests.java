package com.kaiostavares.orderservice;

import com.kaiostavares.orderservice.repositories.OrderRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.containers.MySQLContainer;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {

    @ServiceConnection
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.4");
    @LocalServerPort
    private Integer port;
    @Autowired
    private OrderRepository orderRepository;


    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static {
        mySQLContainer.start();
    }

    @Test
    void shouldSubmitOrder() {
        final String submitOrderJson = """
                {
                     "skuCode": "iphone_15",
                     "price": 1000,
                     "quantity": 1
                }
                """;
        final var responseBodyString = RestAssured.given()
                .contentType("application/json")
                .body(submitOrderJson)
                .when()
                .post("/api/orders")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .body().asString();
        Assertions.assertEquals("Order Placed Successfully", responseBodyString);
        Assertions.assertEquals(1, orderRepository.findAll().size());
    }

}
