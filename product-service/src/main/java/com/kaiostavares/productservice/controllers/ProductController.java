package com.kaiostavares.productservice.controllers;

import com.kaiostavares.productservice.dtos.ProductRequestDto;
import com.kaiostavares.productservice.dtos.ProductResponseDto;
import com.kaiostavares.productservice.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequestDto request) {
        productService.createProduct(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }
}
