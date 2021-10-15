package com.example.loja.exception;

public class PurchaseNotFound extends RuntimeException {
    public PurchaseNotFound() {
        super("Purchase not found.");
    }
    public PurchaseNotFound(String message) {
        super(message);
    }
}