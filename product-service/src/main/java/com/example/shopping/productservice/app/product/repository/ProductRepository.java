package com.example.shopping.productservice.app.product.repository;

import com.example.shopping.productservice.app.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
