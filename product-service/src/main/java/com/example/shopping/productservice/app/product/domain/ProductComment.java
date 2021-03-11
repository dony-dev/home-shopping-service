package com.example.shopping.productservice.app.product.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "tb_product_comment")
@Entity
public class ProductComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Lob
    private String text;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Product product;

    public ProductComment() {
    }

    public ProductComment(Long commentId, String text, @NotNull Product product) {
        this.commentId = commentId;
        this.text = text;
        this.product = product;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductComment{" +
                "commentId=" + commentId +
                ", text='" + text + '\'' +
                ", product=" + product +
                '}';
    }
}
