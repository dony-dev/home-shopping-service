package com.example.shopping.orderservice.app.order.shared;

import java.math.BigDecimal;

public class OrderResponse {
    private Long orderId;
    private String orderName;
    private String orderDescription;
    private BigDecimal orderPrice;

    private String productIds;

    public OrderResponse() {
    }

    public OrderResponse(Long orderId, String orderName, String orderDescription, BigDecimal orderPrice, String productIds) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.orderDescription = orderDescription;
        this.orderPrice = orderPrice;
        this.productIds = productIds;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

}
