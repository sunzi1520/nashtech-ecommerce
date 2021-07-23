package com.nashtech.ecommerce.service;

import com.nashtech.ecommerce.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public List<UserDto> retrieveUsers();

    public UserDto getUserById(Long userId);

    public UserDto saveUser(UserDto user);

    public boolean deleteUser(Long userId);

    public boolean updateUser(UserDto userDto);

    public boolean existsByUsername(String username);

    public boolean existsById(Long userId);

    public List<UserDto> getAllByUsername(String username);

    public UserDto getUserByUsername(String username);

}
