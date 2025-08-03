package com.kaiostavares.productservice.services;

import com.kaiostavares.productservice.dtos.ProductRequestDto;
import com.kaiostavares.productservice.dtos.ProductResponseDto;
import com.kaiostavares.productservice.models.Product;
import com.kaiostavares.productservice.respositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequestDto productRequest) {
        final var product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponseDto> getAllProducts() {
        final List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::mapToProductResponseDto)
                .toList();
    }

    private ProductResponseDto mapToProductResponseDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice());
    }
}
