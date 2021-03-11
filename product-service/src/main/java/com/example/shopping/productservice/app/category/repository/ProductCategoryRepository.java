package com.example.shopping.productservice.app.category.repository;

import com.example.shopping.productservice.app.category.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
