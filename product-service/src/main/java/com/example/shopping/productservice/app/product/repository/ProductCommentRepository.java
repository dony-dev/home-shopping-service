package com.example.shopping.productservice.app.product.repository;

import com.example.shopping.productservice.app.product.domain.Product;
import com.example.shopping.productservice.app.product.domain.ProductComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCommentRepository extends JpaRepository<ProductComment, Long> {

    List<ProductComment> findByProduct(Product product);
}
