package com.example.shopping.orderservice.app.order.shared;

import com.fasterxml.jackson.annotation.JsonView;

import java.math.BigDecimal;

public class Product {

    private Long productId;
    private BigDecimal productPrice;
    private Integer quantity;

//    private Long orderId;

    public Product() {
    }

    public Product(Long productId, BigDecimal productPrice) {
        this.productId = productId;
        this.productPrice = productPrice;
    }

    public Product(Long productId, BigDecimal productPrice, Integer quantity) {
        this.productId = productId;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
