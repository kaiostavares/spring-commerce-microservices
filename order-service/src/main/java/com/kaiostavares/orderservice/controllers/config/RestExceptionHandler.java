package com.kaiostavares.orderservice.controllers.config;

import com.kaiostavares.orderservice.exceptions.OutOfStockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OutOfStockException.class)
    private ResponseEntity<RestErrorMessage> handleOutOfStockException(OutOfStockException ex) {
        final var threatResponse = new RestErrorMessage(HttpStatus.CONFLICT, ex.getMessage());
        return ResponseEntity.status(threatResponse.status()).body(threatResponse);
    }
}
