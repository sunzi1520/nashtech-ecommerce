package com.nashtech.ecommerce.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDto {

    private Long id;

    private String name;

    private Float price;

    private String description;

    private byte[] image;

    private Float avg_rating;

    private List<CategoryDto> categories = new ArrayList<>();

}
