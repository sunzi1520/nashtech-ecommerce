package com.nashtech.ecommerce.restcontroller;


import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nashtech.ecommerce.dto.MessageResponse;
import com.nashtech.ecommerce.dto.SignupRequest;
import com.nashtech.ecommerce.dto.UserDto;
import com.nashtech.ecommerce.enumeration.ERoleName;
import com.nashtech.ecommerce.service.UserService;
import com.nashtech.ecommerce.view.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAll() {
        MappingJacksonValue usersJson = new MappingJacksonValue(userService.retrieveUsers());
        usersJson.setSerializationView(Views.Public.class);
        return new ResponseEntity<>(usersJson, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('CUSTOMER') and #userId==authentication.principal.id)")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getById(@PathVariable("userId") Long userId) {
        UserDto userDto = userService.getUserById(userId);

        if (userDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MappingJacksonValue userJson = new MappingJacksonValue(userDto);
        userJson.setSerializationView(Views.Public.class);

        return new ResponseEntity<>(userJson, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> saveUser(@Validated @RequestBody UserDto userDto) {
        userDto.setUsername(userDto.getUsername().toLowerCase().trim());
        if (userService.existsByUsername(userDto.getUsername())) {
            return new ResponseEntity(new MessageResponse("Error: Username is already taken!"), HttpStatus.BAD_REQUEST);
        }

        userDto.setPassword(encoder.encode(userDto.getPassword()));
        Set<Long> roles = userDto.getRoles();
        if (roles == null) {
            roles = new HashSet<>();
        }
        if (roles.size() == 0) {
            roles.add(ERoleName.ROLE_CUSTOMER.ordinal() + 1L);
        }
        userDto.setRoles(roles);

        MappingJacksonValue userJson = new MappingJacksonValue(userService.saveUser(userDto));
        userJson.setSerializationView(Views.Public.class);

        return new ResponseEntity<>(userJson, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('CUSTOMER') and #userId==authentication.principal.id)")
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @Validated @RequestBody UserDto userDto) {

        if (!userService.existsById(userId)) {
            return new ResponseEntity(new MessageResponse("Error: User not found!"), HttpStatus.NOT_FOUND);
        }

        if (userDto.getId() == null || userId != userDto.getId()) {
            userDto.setId(userId);
        }

        userDto.setUsername(userDto.getUsername().toLowerCase().trim());

        UserDto existingUsers = userService.getUserByUsername(userDto.getUsername());
        if (existingUsers.getId() != userDto.getId()) {
            return new ResponseEntity(new MessageResponse("Error: Username is already taken!"), HttpStatus.BAD_REQUEST);
        }

        if ((userDto.getPassword() != null) && (!userDto.getPassword().isEmpty())){
            userDto.setPassword(encoder.encode(userDto.getPassword()));
        }
        if (userService.updateUser(userDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        };

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        if (userService.deleteUser(userId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}