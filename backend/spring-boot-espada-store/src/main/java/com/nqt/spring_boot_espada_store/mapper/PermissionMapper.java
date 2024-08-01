package com.nqt.spring_boot_espada_store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.nqt.spring_boot_espada_store.dto.request.PermissionRequest;
import com.nqt.spring_boot_espada_store.dto.response.PermissionResponse;
import com.nqt.spring_boot_espada_store.entity.Permission;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PermissionMapper {

    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
