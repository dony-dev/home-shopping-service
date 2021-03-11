package com.example.shopping.productservice.app.zinit.web;

import com.example.shopping.productservice.app.category.domain.ProductCategory;
import com.example.shopping.productservice.app.category.service.ProductCategoryService;
import com.example.shopping.productservice.app.product.domain.Product;
import com.example.shopping.productservice.app.product.service.ProductCommentService;
import com.example.shopping.productservice.app.product.service.ProductService;
import com.example.shopping.productservice.app.zinit.service.InitDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/init")
public class InitDataController {

    private final InitDataService initDataService;
    private final ProductCategoryService productCategoryService;
    private final ProductService productService;
    private final ProductCommentService productCommentService;

    public InitDataController(InitDataService initDataService, ProductCategoryService productCategoryService, ProductService productService, ProductCommentService productCommentService) {
        this.initDataService = initDataService;
        this.productCategoryService = productCategoryService;
        this.productService = productService;
        this.productCommentService = productCommentService;
    }

    @RequestMapping(value = "/category")
    public ResponseEntity<List<ProductCategory>> initProductCategoryData() throws IOException {
        initDataService.initProductCategoryData();
        return ResponseEntity.status(HttpStatus.OK).body(productCategoryService.findAll());
    }

    @RequestMapping(value = "/product")
    public ResponseEntity<List<Product>> initProductData() throws IOException {
        initDataService.initProductData();
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
    }

    @RequestMapping(value = "/comment")
    public ResponseEntity<Void> initProductComment() throws IOException {
        initDataService.initProductCommentData();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
