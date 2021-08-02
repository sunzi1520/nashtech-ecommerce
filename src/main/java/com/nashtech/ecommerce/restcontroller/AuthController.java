package com.nashtech.ecommerce.restcontroller;

import com.nashtech.ecommerce.dto.*;
import com.nashtech.ecommerce.enumeration.ERoleName;
import com.nashtech.ecommerce.security.jwt.JwtUtils;
import com.nashtech.ecommerce.security.service.UserDetailsImpl;
import com.nashtech.ecommerce.service.UserService;
import com.nashtech.ecommerce.view.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername().toLowerCase().trim(),
                        loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new ResponseEntity(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signupRequest) {
        if (userService.existsByUsername(signupRequest.getUsername())) {
            return new ResponseEntity(new MessageResponse("Error: Username is already taken!"), HttpStatus.BAD_REQUEST);
        }

        UserDto userDto = new UserDto();
        userDto.setUsername(signupRequest.getUsername().toLowerCase().trim());
        userDto.setPassword(encoder.encode(signupRequest.getPassword()));
        Set<Long> roles = signupRequest.getRoles();
        if (roles == null){
            roles = new HashSet<>();
        }
        if (roles.size() == 0) {
            roles.add(ERoleName.ROLE_CUSTOMER.ordinal()+1L);
        }
        userDto.setRoles(signupRequest.getRoles());
        userDto.setFirstname(signupRequest.getFirstname());
        userDto.setLastname(signupRequest.getLastname());
        userDto.setGender(signupRequest.isGender());
        userDto.setDob(signupRequest.getDob());
        userDto.setEmail(signupRequest.getEmail());
        userDto.setPhonenumber(signupRequest.getPhonenumber());

        MappingJacksonValue userJson = new MappingJacksonValue(userService.saveUser(userDto));
        userJson.setSerializationView(Views.Public.class);

        return new ResponseEntity<>(userJson, HttpStatus.OK);

    }
}
