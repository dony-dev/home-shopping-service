package com.example.shopping.productservice.app.category.service;

import com.example.shopping.productservice.app.category.domain.ProductCategory;
import com.example.shopping.productservice.app.category.dto.ProductCategoryRequest;
import com.example.shopping.productservice.app.category.mapper.ProductCategoryMapper;
import com.example.shopping.productservice.app.category.repository.ProductCategoryRepository;
import com.example.shopping.productservice.app.product.domain.Product;
import com.example.shopping.productservice.exception.ProductCategoryException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryMapper productCategoryMapper;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository, ProductCategoryMapper productCategoryMapper) {
        this.productCategoryRepository = productCategoryRepository;
        this.productCategoryMapper = productCategoryMapper;
    }

    public void createProductCategory(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
    }

    public void createProductCategory(ProductCategoryRequest productCategoryRequest) {
        productCategoryRepository.save(productCategoryMapper.mapDtoToProductCategory(productCategoryRequest));
    }

    public ProductCategory getProductCategory(Long categoryId) {
        ProductCategory productCategory = productCategoryRepository.findById(categoryId).orElseThrow(() -> new ProductCategoryException(""));
        return productCategory;
    }

    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }


    public Page<ProductCategory> findAllByPage(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductCategory> productCategoryPage = productCategoryRepository.findAll(pageRequest);
        List<ProductCategory> productCategoryList = productCategoryPage.stream().collect(Collectors.toList());
        return new PageImpl<>(productCategoryList, pageRequest, productCategoryPage.getTotalElements());
    }

    // Update product category by category id
    @Transactional
    public void updateCategory(ProductCategoryRequest productCategoryRequest) {
        Long categoryId = productCategoryRequest.getCategoryId();
        ProductCategory productCategory = productCategoryRepository.findById(categoryId).orElseThrow(() -> new ProductCategoryException("category with " + categoryId + " not found"));
        productCategoryRepository.save(productCategoryMapper.mapDtoToProductCategory(productCategoryRequest));
    }

    // Delete product category by category id
    @Transactional
    public void deleteCategory(Long categoryId) {
        ProductCategory productCategory = productCategoryRepository.findById(categoryId).orElseThrow(() -> new ProductCategoryException("category with " + categoryId + " not found"));
        productCategoryRepository.delete(productCategory);
    }
}
