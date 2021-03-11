package com.example.shopping.productservice.app.product.web;

import com.example.shopping.productservice.app.product.domain.ProductComment;
import com.example.shopping.productservice.app.product.dto.ProductCommentDto;
import com.example.shopping.productservice.app.product.service.ProductCommentService;
import com.example.shopping.productservice.app.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/product/comments")
public class ProductCommentController {

    private final ProductCommentService productCommentService;
    private final ProductService productService;

    public ProductCommentController(ProductCommentService productCommentService, ProductService productService) {
        this.productCommentService = productCommentService;
        this.productService = productService;
    }

    // Create comment for product
    @PostMapping(value = "/comment")
    public ResponseEntity<?> createProductComment(@RequestBody ProductCommentDto productCommentRequest) {
        productCommentService.createProductComment(productCommentRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Get all comments by Product
    @GetMapping(value = "/by-product/{productId}")
    public ResponseEntity<List<ProductComment>> findCommentsByProduct(@PathVariable("productId") Long productId) {
        List<ProductComment> productCommentList = productCommentService.findCommentsByProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(productCommentList);
    }

    // Update comment by comment id
    @PutMapping(value = "/comment/{commentId}/edit")
    public ResponseEntity<Void> updateProductComment(@RequestBody @Valid ProductCommentDto productCommentRequest, @PathVariable("commentId") Long commentId) {
        productCommentService.updateProductComment(productCommentRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Delete comment by comment id
    @DeleteMapping(value = "/comment/{commentId}/delete")
    public ResponseEntity<Void> deleteProductComment(@PathVariable("commentId") Long commentId) {
        productCommentService.deleteProductComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
