package com.example.shopping.productservice.app.category.mapper;

import com.example.shopping.productservice.app.category.domain.ProductCategory;
import com.example.shopping.productservice.app.category.dto.ProductCategoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {

    ProductCategory mapDtoToProductCategory(ProductCategoryRequest productCategoryRequest);

}
