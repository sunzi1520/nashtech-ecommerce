package com.nashtech.ecommerce.utils;

import com.nashtech.ecommerce.dto.UserDto;
import com.nashtech.ecommerce.entity.User;
import org.modelmapper.AbstractConverter;

import java.util.stream.Collectors;

public class UserToUserDtoConverter extends AbstractConverter<User, UserDto> {

    @Override
    protected UserDto convert(User user) {
        UserDto convertedUserDto = new UserDto();
        convertedUserDto.setId(user.getId());
        convertedUserDto.setUsername(user.getUsername());
        convertedUserDto.setPassword(user.getPassword());
        convertedUserDto.setRoles(
                user.getRoles()
                    .stream()
                    .map(role -> role.getId())
                    .collect(Collectors.toSet())
        );
        convertedUserDto.setFirstname(user.getFirstname());
        convertedUserDto.setLastname(user.getLastname());
        convertedUserDto.setGender(user.isGender());
        convertedUserDto.setDob(user.getDob());
        convertedUserDto.setEmail(user.getEmail());
        convertedUserDto.setPhonenumber(user.getPhonenumber());
        return convertedUserDto;
    }
}
