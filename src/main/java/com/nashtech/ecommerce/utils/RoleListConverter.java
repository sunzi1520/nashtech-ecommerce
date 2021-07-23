package com.nashtech.ecommerce.utils;

import com.nashtech.ecommerce.entity.Role;
import org.modelmapper.AbstractConverter;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleListConverter extends AbstractConverter<Set<Role>, Set<Long> > {

    @Override
    protected Set<Long> convert(Set<Role> roles) {
        return roles.stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
    }
}
