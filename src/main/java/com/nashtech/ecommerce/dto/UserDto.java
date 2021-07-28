package com.nashtech.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.nashtech.ecommerce.view.Views;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.Set;

@Data
public class UserDto {

    @JsonView(Views.Public.class)
    @Nullable
    private Long id;

    @JsonView(Views.Public.class)
    private String username;

    @JsonView(Views.Internal.class)
    private String password;

    @JsonView(Views.Public.class)
    private Set<Long> roles;

    @JsonView(Views.Public.class)
    private String firstname;

    @JsonView(Views.Public.class)
    private String lastname;

    @JsonView(Views.Public.class)
    private boolean gender;

    @JsonView(Views.Public.class)
    private Date dob;

    @JsonView(Views.Public.class)
    private String email;

    @JsonView(Views.Public.class)
    private String phonenumber;
}
