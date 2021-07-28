package com.nashtech.ecommerce.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ratings")
@Data
public class Rating {

    @EmbeddedId
    private RatingId ratingId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "point")
    private Long point;

    @Column(name = "content")
    private String content;

    @Column(name = "postedDate")
    private Date postedDate;

}
