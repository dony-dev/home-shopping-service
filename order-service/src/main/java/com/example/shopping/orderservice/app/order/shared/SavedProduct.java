package com.example.shopping.orderservice.app.order.shared;

import java.math.BigDecimal;

public class SavedProduct {

    private Long productId;

    private String productName;

    private String productDescription;

    private BigDecimal productPrice;

    public SavedProduct() {
    }

    public SavedProduct(Long productId, String productName, String productDescription, BigDecimal productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
}
