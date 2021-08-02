package com.nashtech.ecommerce.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RatingDto {

    private Long userId;

    private String userName;

    private Long productId;

    private String productName;

    private Long point;

    private String content;

    private Date createdDate;

    private  Date updatedDate;

}
