package com.example.shopping.productservice.app.product.service;

import com.example.shopping.productservice.app.product.domain.Product;
import com.example.shopping.productservice.app.product.domain.ProductComment;
import com.example.shopping.productservice.app.product.dto.ProductCommentDto;
import com.example.shopping.productservice.app.product.mapper.ProductCommentMapper;
import com.example.shopping.productservice.app.product.repository.ProductCommentRepository;
import com.example.shopping.productservice.app.product.repository.ProductRepository;
import com.example.shopping.productservice.exception.ProductCommentException;
import com.example.shopping.productservice.exception.ProductServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCommentService {

    private final ProductCommentRepository productCommentRepository;
    private final ProductRepository productRepository;
    private final ProductCommentMapper productCommentMapper;

    public ProductCommentService(ProductCommentRepository productCommentRepository, ProductRepository productRepository, ProductCommentMapper productCommentMapper) {
        this.productCommentRepository = productCommentRepository;
        this.productRepository = productRepository;
        this.productCommentMapper = productCommentMapper;
    }

    // Create product comment
    @Transactional
    public void createProductComment(ProductCommentDto productCommentRequest) {
        Long productId = productCommentRequest.getProductId();
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductServiceException("product with " + productId + " not found"));
        productCommentRepository.save(productCommentMapper.mapToProductComment(productCommentRequest, product));
    }

    // Get product comments by product
    public List<ProductComment> findCommentsByProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductServiceException("product with " + productId + " not found"));
        List<ProductComment> productCommentList = productCommentRepository.findByProduct(product).stream().collect(Collectors.toList());
        return productCommentList;
    }

    // Update product comment by comment id
    @Transactional
    public void updateProductComment(ProductCommentDto productCommentRequest) {
        Long commentId = productCommentRequest.getCommentId();
        Long productId = productCommentRequest.getProductId();
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductServiceException("product with " + productId + " not found"));
        ProductComment productComment = productCommentRepository.findById(commentId).orElseThrow(() -> new ProductCommentException("comment with " + commentId + " not found"));
        productCommentRepository.save(productCommentMapper.mapToProductComment(productCommentRequest, product));
    }

    // Delete comment by comment id
    @Transactional
    public void deleteProductComment(Long commentId) {
        ProductComment productComment = productCommentRepository.findById(commentId).orElseThrow(() -> new ProductCommentException("comment with " + commentId + " not found"));
        productCommentRepository.delete(productComment);
    }
}
