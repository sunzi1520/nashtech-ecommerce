package com.nashtech.ecommerce.service.impl;

import com.nashtech.ecommerce.dto.RoleDto;
import com.nashtech.ecommerce.entity.Role;
import com.nashtech.ecommerce.enumeration.ERoleName;
import com.nashtech.ecommerce.repository.RoleRepository;
import com.nashtech.ecommerce.service.RoleService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();

        return roles.stream()
                    .map(role -> modelMapper.map(role, RoleDto.class))
                    .collect(Collectors.toList());
    }

    @Override
    public RoleDto getRoleById(Long roleId) {
        Role role = roleRepository.findById(roleId)
                        .orElseThrow(() -> new RuntimeException("Error: Role not found."));
        return modelMapper.map(role, RoleDto.class);
    }

    @Override
    public RoleDto getRoleByName(ERoleName name) {
        Role role = roleRepository.findByName(name.name())
                                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
        return modelMapper.map(role, RoleDto.class);
    }

    @Override
    @Transactional
    public RoleDto saveRole(RoleDto roleDto) {

        Optional<Role> existingRole = roleRepository.findByName(roleDto.getName());

        if (existingRole.isPresent()) {
            throw new RuntimeException("Role existing");
        }

        Role role = modelMapper.map(roleDto, Role.class);

        return modelMapper.map(roleRepository.save(role), RoleDto.class);
    }

    @Override
    @Transactional
    public boolean deleteRole(Long roleId) {
        Optional<Role> existingRole = roleRepository.findById(roleId);

        if (existingRole.isPresent()) {
            roleRepository.deleteById(roleId);
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean updateRole(RoleDto roleDto) {
        Optional<Role> existingRole = roleRepository.findById(roleDto.getId());

        if (existingRole.isPresent()) {
            Role updatedRole = existingRole.map(role -> modelMapper.map(roleDto, Role.class)).get();
            roleRepository.save(updatedRole);
            return true;
        }

        return false;
    }
}
