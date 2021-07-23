package com.nashtech.ecommerce.service;

import com.nashtech.ecommerce.dto.RoleDto;
import com.nashtech.ecommerce.entity.Role;
import com.nashtech.ecommerce.enumeration.ERoleName;

import java.util.List;

public interface RoleService {

    public List<RoleDto> getAllRoles();

    public RoleDto getRoleById(Long roleId);

    public RoleDto getRoleByName(ERoleName name);

    public RoleDto saveRole(RoleDto roleDto);

    public boolean deleteRole(Long roleId);

    public boolean updateRole(RoleDto roleDto);

}
