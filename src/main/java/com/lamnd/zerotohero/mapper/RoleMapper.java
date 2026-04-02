package com.lamnd.zerotohero.mapper;

import com.lamnd.zerotohero.dto.reponse.PermissionResponse;
import com.lamnd.zerotohero.dto.reponse.RoleResponse;
import com.lamnd.zerotohero.dto.request.PermissionRequest;
import com.lamnd.zerotohero.dto.request.RoleRequest;
import com.lamnd.zerotohero.entity.Permission;
import com.lamnd.zerotohero.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    RoleResponse toDTO(Role role);
    List<RoleResponse> toListDTO(List<Role> roles);
}
