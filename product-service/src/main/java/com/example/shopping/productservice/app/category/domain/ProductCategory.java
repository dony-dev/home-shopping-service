package com.example.shopping.productservice.app.category.domain;

import com.example.shopping.productservice.app.product.domain.Product;
import com.example.shopping.productservice.app.category.util.ProductCategoryJsonView;
import com.example.shopping.productservice.app.product.util.ProductJsonView;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.List;

@Table(name = "tb_product_category")
@Entity
//@JsonView({ProductCategoryJsonView.CategoryList.class})
@JsonView({
        ProductCategoryJsonView.CategoryList.class, ProductCategoryJsonView.CategoryDetail.class,
        ProductJsonView.ProductList.class, ProductJsonView.ProductDetail.class
})
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String categoryName;

    //    @JsonView({ProductCategoryJsonView.CategoryList.class})
    @Lob
    private String categoryDescription;

    /* */
//    @JsonView({ProductCategoryJsonView.CategoryDetail.class})
    @JsonView({ProductCategoryJsonView.CategoryDetail.class,
            ProductJsonView.ProductDetail.class})
    @OneToMany
    private List<Product> categoryProducts;

    public ProductCategory() {
    }

    public ProductCategory(Long categoryId, String categoryName, String categoryDescription, List<Product> categoryProducts) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryProducts = categoryProducts;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public List<Product> getCategoryProducts() {
        return categoryProducts;
    }

    public void setCategoryProducts(List<Product> categoryProducts) {
        this.categoryProducts = categoryProducts;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryDescription='" + categoryDescription + '\'' +
                ", categoryProducts=" + categoryProducts +
                '}';
    }
}
