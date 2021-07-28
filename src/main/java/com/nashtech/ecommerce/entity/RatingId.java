package com.nashtech.ecommerce.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RatingId implements Serializable {

    private static final long serialVersionUID =1L;

    @Column(name="user_id")
    private Long userId;

    @Column(name="product_id")
    private Long productId;

    public RatingId() {}

    public RatingId(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
