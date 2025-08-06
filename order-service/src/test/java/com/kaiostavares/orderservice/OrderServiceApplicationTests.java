package com.kaiostavares.orderservice;

import com.kaiostavares.orderservice.controllers.config.RestErrorMessage;
import com.kaiostavares.orderservice.repositories.OrderRepository;
import com.kaiostavares.orderservice.stub.InventoryClientStub;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.testcontainers.containers.MySQLContainer;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
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
                     "skuCode": "iphone_15_blue",
                     "price": 1000,
                     "quantity": 1
                }
                """;
        InventoryClientStub.stubInventoryCall("iphone_15_blue", 1, "true");
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


    @Test
    void shouldNotSubmitOrderWhenOutOfStock() {
        final String submitOrderJson = """
                {
                     "skuCode": "iphone_15_blue",
                     "price": 1000,
                     "quantity": 1
                }
                """;
        InventoryClientStub.stubInventoryCall("iphone_15_blue", 1, "false");
        final var responseBody = RestAssured.given()
                .contentType("application/json")
                .body(submitOrderJson)
                .when()
                .post("/api/orders")
                .then()
                .log().all()
                .statusCode(409)
                .extract()
                .body().as(RestErrorMessage.class);
        Assertions.assertEquals(
                new RestErrorMessage(HttpStatus.CONFLICT, "Product is out of stock"),
                responseBody
        );
        Assertions.assertEquals(0, orderRepository.findAll().size());
    }

}
