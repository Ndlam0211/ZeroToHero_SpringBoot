package com.lamnd.zerotohero.mapper;

import com.lamnd.zerotohero.dto.reponse.PermissionResponse;
import com.lamnd.zerotohero.dto.request.PermissionRequest;
import com.lamnd.zerotohero.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    void updatePermission(@MappingTarget Permission permission, PermissionRequest request);
    PermissionResponse toDTO(Permission permission);
    List<PermissionResponse> toListDTO(List<Permission> permissions);
}
