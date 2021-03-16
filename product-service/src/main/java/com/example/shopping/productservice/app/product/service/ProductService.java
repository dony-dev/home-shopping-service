package com.example.shopping.productservice.app.product.service;

import com.example.shopping.productservice.app.category.domain.ProductCategory;
import com.example.shopping.productservice.app.category.repository.ProductCategoryRepository;
import com.example.shopping.productservice.app.product.domain.Product;
import com.example.shopping.productservice.app.product.dto.ProductRequest;
import com.example.shopping.productservice.app.product.mapper.ProductMapper;
import com.example.shopping.productservice.app.product.repository.ProductRepository;
import com.example.shopping.productservice.exception.ProductServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productMapper = productMapper;
    }

    // create product
    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    // Create product
    @Transactional
    public void createProduct(ProductRequest productRequest) {
        Long categoryId = productRequest.getCategoryId();
        ProductCategory productCategory = productCategoryRepository.findById(categoryId).orElseThrow(() -> new ProductServiceException("category with " + categoryId + " not found"));
        Product product = productMapper.mapToProduct(productRequest, productCategory);

        productRepository.save(product);
    }

//    public Product orderProduct(Product product) {
//        Product oldProduct = productRepository.findById(product.getProductId()).orElseThrow(() -> new ProductServiceException(""));
//        return productRepository.save(product);
//    }

    // Get product by product id
    public Product findById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductServiceException("product with " + productId + " not found"));
        return product;
    }

    // Get all products
    public List<Product> findAll() {
        List<Product> productList = productRepository.findAll();
        return productList;
    }

    // Get products by page
    public Page<Product> findAllByPage(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageRequest);
        List<Product> productListPage = productPage.stream().collect(Collectors.toList());
        return new PageImpl<>(productListPage, pageRequest, productPage.getTotalElements());
    }

    // Update product by product id
    @Transactional
    public void update(Product productRequest) {
        Long productId = productRequest.getProductId();
        Product product = productRepository.findById(productRequest.getProductId()).orElseThrow(() -> new ProductServiceException("product with " + productId + " not found"));

        Long categoryId = product.getProductCategory().getCategoryId();
        ProductCategory productCategory = productCategoryRepository.findById(categoryId).orElseThrow(() -> new ProductServiceException("category with " + categoryId + " not found"));

        productRequest.setProductCategory(productCategory);

        productRepository.save(productRequest);
    }

    // Delete product by product id
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductServiceException("product with " + productId + " not found"));
        productRepository.delete(product);
    }

    // Find all by multiple product ids
    public List<Product> findAllByMultipleIds(Map<String, List<Long>> params) {
        List<Product> productList = new ArrayList<>();
        for (Long productId : params.get("productIds")) {
            Product product = productRepository.findById(productId).orElseThrow(() -> new ProductServiceException("product with " + productId + " not found"));
            productList.add(product);
        }
        log.info("productList: {}", productList);
        return productList;
    }

    public List<Product> findAllByIdLIst(List<Long> productIds) {
        List<Product> productList = new ArrayList<>();
        for (Long productId : productIds) {
            Product product = productRepository.findById(productId).orElseThrow(() -> new ProductServiceException("product with " + productId + " not found"));
            productList.add(product);
        }
        log.info("productList: {}", productList);
        return productList;
    }
}
