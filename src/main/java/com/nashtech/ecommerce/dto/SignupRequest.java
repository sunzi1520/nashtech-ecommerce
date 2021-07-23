package com.nashtech.ecommerce.dto;

import java.util.Set;

public class SignupRequest {
    private String username;
    private String password;
    private Set<Long> roles;

    public SignupRequest() {
        super();
    }

    public SignupRequest(String username, String password, Set<Long> roles) {
        super();
        this.username = username;
        this.password = password;
        this.roles = roles;
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
}
