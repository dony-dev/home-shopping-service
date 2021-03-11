package com.example.shopping.productservice.app.product.mapper;

import com.example.shopping.productservice.app.product.domain.Product;
import com.example.shopping.productservice.app.product.domain.ProductComment;
import com.example.shopping.productservice.app.product.dto.ProductCommentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCommentMapper {

    ProductComment mapToProductComment(ProductCommentDto productCommentRequest, Product product);

}
