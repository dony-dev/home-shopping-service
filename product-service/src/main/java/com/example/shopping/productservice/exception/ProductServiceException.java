package com.example.shopping.productservice.exception;

public class ProductServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProductServiceException(String message) {
        super(message);
    }
}