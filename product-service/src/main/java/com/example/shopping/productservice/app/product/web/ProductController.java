package com.example.shopping.productservice.app.product.web;

import com.example.shopping.productservice.app.product.domain.Product;
import com.example.shopping.productservice.app.product.dto.ProductRequest;
import com.example.shopping.productservice.app.product.service.ProductService;
import com.example.shopping.productservice.app.product.util.ProductJsonView;
import com.example.shopping.productservice.exception.ErrorUtil;
import com.example.shopping.productservice.exception.ProductServiceException;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/test")
    public ResponseEntity<String> testApiCall() {
        return ResponseEntity.ok("success");
    }

    @PostMapping(value = "/product")
    public ResponseEntity<?> saveProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/product/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @PostMapping(value = "/order-product")
//    public ResponseEntity<?> orderProduct(@RequestBody Product product) {
//        return ResponseEntity.status(HttpStatus.OK).body(productService.orderProduct(product));
//    }

//    @GetMapping(value = "/product/{productId}")
//    public ResponseEntity<Product> getProductById(@PathVariable("productId") Long productId) {
//        Product product = productService.findById(productId);
//        return ResponseEntity.status(HttpStatus.OK).body(product);
//    }
    @JsonView(ProductJsonView.ProductDetail.class)
    @GetMapping(value = "/product/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId") Long productId) {
        if (productId == null) {
            throw new ProductServiceException("No product Id");
        }
        Product product = productService.findById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping(value = "/product/{productId}/edit")
    public ResponseEntity<?> updateProduct(@RequestBody @Valid Product product, @PathVariable("productId") Long productId) {
        productService.update(product);
//        return new ResponseEntity<>(HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(productService.findById(productId));
    }

//    @DeleteMapping(value = "/product/{productId}/delete")
//    public ResponseEntity<?> deleteProduct(@PathVariable("productId") Long productId) {
//        productService.deleteProduct(productId);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
    @DeleteMapping(value = "/product/{productId}/delete")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") Long productId) {
        if (productId == null) {
            return ErrorUtil.create(HttpStatus.BAD_REQUEST,"bad request", "no path variable");
        }
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/page")
    public Page<Product> getProductByPagination(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return productService.findAllByPage(page, size);
    }

    @JsonView({ProductJsonView.ProductList.class})
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
    }

    // Find all by multiple product Ids
    @GetMapping(value = "/product/eureka")
    public ResponseEntity<List<Product>> getProductByMultipleIds(@RequestParam(name = "productIds") List<Long> params) {
        List<Product> productList = productService.findAllByIdLIst(params);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }
}
