package com.kaiostavares.orderservice.exceptions;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String message) {
        super(message);
    }
    public OutOfStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
