package com.nashtech.ecommerce.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ProductCategoriesId implements Serializable {

    private static final long serialVersionUID =1L;

    @Column(name="product_id")
    private Long productId;

    @Column(name="category_id")
    private Long categoryId;

    public ProductCategoriesId() {
        super();
    }

    public ProductCategoriesId(Long productId, Long categoryId) {
        super();
        this.productId = productId;
        this.categoryId = categoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
