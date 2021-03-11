package com.example.shopping.productservice.app.product.mapper;

import com.example.shopping.productservice.app.category.domain.ProductCategory;
import com.example.shopping.productservice.app.product.domain.Product;
import com.example.shopping.productservice.app.product.dto.ProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

//    @Mapping(target = "productCategory", source = "productCategory")
    public abstract Product mapToProduct(ProductRequest productRequest, ProductCategory productCategory);

}
