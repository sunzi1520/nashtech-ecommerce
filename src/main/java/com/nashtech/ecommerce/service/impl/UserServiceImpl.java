package com.nashtech.ecommerce.service.impl;

import com.nashtech.ecommerce.dto.UserDto;
import com.nashtech.ecommerce.entity.Role;
import com.nashtech.ecommerce.entity.User;
import com.nashtech.ecommerce.repository.RoleRepository;
import com.nashtech.ecommerce.repository.UserRepository;
import com.nashtech.ecommerce.service.UserService;
import com.nashtech.ecommerce.utils.UserToUserDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    private UserDto convertUserToUserDto(User user) {
        return modelMapper.typeMap(User.class, UserDto.class)
                .setConverter(new UserToUserDtoConverter())
                .map(user);
    }

    @Override
    public List<UserDto> retrieveUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> convertUserToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long userId) {

        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            return null;
        }

        return convertUserToUserDto(user.get());
    }

    @Override
    @Transactional
    public UserDto saveUser(UserDto userDto) {

        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RuntimeException("User existing");
        }

        User user = modelMapper.map(userDto, User.class);
        Set<Role> roles;

        try {
            roles = userDto.getRoles().stream()
                .map((roleId) -> {
                    Role role = roleRepository.findById(roleId)
                                    .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                    return role;
                })
                .collect(Collectors.toSet());

            user.setRoles(roles);

            User savedUser = userRepository.save(user);

            return  convertUserToUserDto(savedUser);
        } catch(RuntimeException e) {
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean deleteUser(Long userId) {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean updateUser(UserDto userDto) {
        Optional<User> existingUser = userRepository.findById(userDto.getId());
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            modelMapper.map(userDto, user);
            if ((userDto.getRoles() != null) && (!userDto.getRoles().isEmpty())){
                Set<Role> roles;
                try {
                    roles = userDto.getRoles().stream()
                            .map((roleId) -> {
                                Role role = roleRepository.findById(roleId)
                                        .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                                return role;
                            })
                            .collect(Collectors.toSet());

                    user.setRoles(roles);
                } catch(RuntimeException e) {
                    throw e;
                }
            }

            userRepository.save(user);

            return true;
        }

        return false;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsById(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public List<UserDto> getAllByUsername(String username) {
        List<User> users = userRepository.findAllByUsername(username);

        return users.stream()
                    .map(user -> convertUserToUserDto(user))
                    .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByUsername(String username) {

        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent()) {
            return null;
        }

        return convertUserToUserDto(user.get());
    }

}
