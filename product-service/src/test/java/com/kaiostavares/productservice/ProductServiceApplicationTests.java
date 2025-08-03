package com.kaiostavares.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiostavares.productservice.dtos.ProductRequestDto;
import com.kaiostavares.productservice.respositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8.0");
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ProductRepository productRepository;

	static {
		mongoDBContainer.start();
	}

	@DynamicPropertySource
	static void setup(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@BeforeEach
	void setUp() {
		productRepository.deleteAll();
	}

	@Test()
	void shouldCreateProduct() throws Exception {
		final var productRequest = new ProductRequestDto(
				"iPhone 15",
				"Latest Apple smartphone",
				BigDecimal.valueOf(999.99)
		);
		mockMvc.perform(post("/api/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(productRequest)))
				.andExpect(status().isCreated());
		Assertions.assertEquals(1, productRepository.findAll().size());
	}

	@Test
	void shouldGetAllProducts() throws Exception {
		final var product1 = new ProductRequestDto(
				"iPhone 15", "Apple smartphone", BigDecimal.valueOf(999.99)
		);
		final var product2 = new ProductRequestDto(
				"MacBook Pro", "Apple laptop", BigDecimal.valueOf(2499.99)
		);
		mockMvc.perform(post("/api/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(product1)))
				.andExpect(status().isCreated());

		mockMvc.perform(post("/api/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(product2)))
				.andExpect(status().isCreated());

		mockMvc.perform(get("/api/products"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].name").value("iPhone 15"))
				.andExpect(jsonPath("$[1].name").value("MacBook Pro"));
	}

	@Test
	void shouldHandleCompleteWorkflow() throws Exception {
		mockMvc.perform(get("/api/products"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(0));
		final var productRequest = new ProductRequestDto(
				"Test Product", "Test Description", BigDecimal.valueOf(50.00)
		);
		mockMvc.perform(post("/api/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(productRequest)))
				.andExpect(status().isCreated());
		mockMvc.perform(get("/api/products"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].name").value("Test Product"));
		Assertions.assertEquals(1, productRepository.findAll().size());
	}
}

