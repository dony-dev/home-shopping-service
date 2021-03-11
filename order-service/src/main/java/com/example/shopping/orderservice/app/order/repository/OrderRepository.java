package com.example.shopping.orderservice.app.order.repository;

import com.example.shopping.orderservice.app.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
