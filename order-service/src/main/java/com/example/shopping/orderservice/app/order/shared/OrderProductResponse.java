package com.example.shopping.orderservice.app.order.shared;

import java.math.BigDecimal;
import java.util.List;

public class OrderProductResponse {

    private Long orderId;
    private BigDecimal totalPrice;
    private List<Product> products;
    private String message;

    public OrderProductResponse() {
    }

    public OrderProductResponse(Long orderId, BigDecimal totalPrice, List<Product> products, String message) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.products = products;
        this.message = message;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
