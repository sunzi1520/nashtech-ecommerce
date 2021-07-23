package com.nashtech.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.nashtech.ecommerce.view.Views;

import java.util.Date;
import java.util.Set;

public class UserDto {

    @JsonView(Views.Public.class)
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


    public UserDto() {
        super();
    }

    public UserDto(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public UserDto(Long id, String username, String password, Set<Long> roles) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public UserDto(Long id, String username, String password, Set<Long> roles,
                   String firstname, String lastname, boolean gender, Date dob,
                   String email, String phonenumber) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.phonenumber = phonenumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Long> getRoles() {
        return roles;
    }

    public void setRoles(Set<Long> roles) {
        this.roles = roles;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
