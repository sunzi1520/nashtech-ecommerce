package com.nashtech.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.nashtech.ecommerce.view.Views;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class CategoryDto {

    @JsonView(Views.Public.class)
    private Long id;

    @JsonView(Views.Public.class)
    private String name;

    @JsonView(Views.Public.class)
    private String description;

}
