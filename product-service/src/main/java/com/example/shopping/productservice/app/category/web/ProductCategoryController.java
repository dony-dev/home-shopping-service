package com.example.shopping.productservice.app.category.web;

import com.example.shopping.productservice.app.category.domain.ProductCategory;
import com.example.shopping.productservice.app.category.dto.ProductCategoryRequest;
import com.example.shopping.productservice.app.category.service.ProductCategoryService;
import com.example.shopping.productservice.app.product.domain.Product;
import com.example.shopping.productservice.exception.ErrorUtil;
import com.example.shopping.productservice.exception.ProductCategoryException;
import com.example.shopping.productservice.exception.ProductServiceException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    // create category
    @PostMapping(value = "/category")
    public ResponseEntity<?> createCategory(@RequestBody ProductCategory productCategory) {
        productCategoryService.createProductCategory(productCategory);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // create category with category request
    @PostMapping(value = "/category/add")
    public ResponseEntity<?> addCategory(@RequestBody ProductCategoryRequest productCategoryRequest) {
        productCategoryService.createProductCategory(productCategoryRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Get product category by category id
    @GetMapping(value = "/category/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(productCategoryService.getProductCategory(categoryId));
    }

    // Get all product categories
    @GetMapping
    public ResponseEntity<List<ProductCategory>> findAllProductCategory() {
        return ResponseEntity.status(HttpStatus.OK).body(productCategoryService.findAll());
    }

    // Get all product category by page
    @GetMapping(value = "/page")
    public Page<ProductCategory> getProductCategoryByPagination(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return productCategoryService.findAllByPage(page, size);
    }

    // Update category by category id
//    @PutMapping(value = "/category/{categoryId}/edit")
//    public ResponseEntity<?> updateCategory(@RequestBody @Valid ProductCategoryRequest productCategoryRequest, @PathVariable("categoryId") Long categoryId) {
    @PutMapping(value = "/category/edit")
    public ResponseEntity<?> updateCategory(@RequestBody @Valid ProductCategoryRequest productCategoryRequest) {
        productCategoryService.updateCategory(productCategoryRequest);
        if (productCategoryRequest.getCategoryId() == null) {
            return ErrorUtil.create(HttpStatus.BAD_REQUEST, "no category id");
        }
//        return new ResponseEntity<>(HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(productCategoryService.getProductCategory(productCategoryRequest.getCategoryId()));
    }

    // Delete category by category id
    @DeleteMapping(value = "/category/{categoryId}/delete")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        if (categoryId == null) {
            throw new ProductCategoryException("No category Id");
        }
        productCategoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
