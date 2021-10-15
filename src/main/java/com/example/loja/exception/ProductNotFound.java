package com.example.loja.exception;

public class ProductNotFound extends RuntimeException {
    public ProductNotFound() {
        super("Product not found.");
    }
    public ProductNotFound(String message) {
        super(message);
    }
}