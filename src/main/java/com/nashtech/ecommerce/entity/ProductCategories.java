package com.nashtech.ecommerce.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_categories")
@Data
public class ProductCategories {

    @EmbeddedId
    private ProductCategoriesId productCategoriesId;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;

}
