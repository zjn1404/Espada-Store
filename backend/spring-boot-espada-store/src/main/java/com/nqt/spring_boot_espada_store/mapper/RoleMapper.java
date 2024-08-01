package com.nqt.spring_boot_espada_store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.nqt.spring_boot_espada_store.dto.request.RoleRequest;
import com.nqt.spring_boot_espada_store.dto.response.RoleResponse;
import com.nqt.spring_boot_espada_store.entity.Role;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
