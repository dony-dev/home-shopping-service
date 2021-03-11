package com.example.shopping.productservice.app.product.dto;

public class ProductCommentDto {

    private Long commentId;
    private String text;

    private Long productId;

    public ProductCommentDto() {
    }

    public ProductCommentDto(Long commentId, String text, Long productId) {
        this.commentId = commentId;
        this.text = text;
        this.productId = productId;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
