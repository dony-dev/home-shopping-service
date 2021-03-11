package com.example.shopping.orderservice.app.order.shared;

import com.example.shopping.orderservice.app.order.domain.Order;

import java.util.List;

public class OrderProductRequest {

    private Order order;
    private List<Product> products;

    public OrderProductRequest() {
    }

    public OrderProductRequest(Order order, List<Product> products) {
        this.order = order;
        this.products = products;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
