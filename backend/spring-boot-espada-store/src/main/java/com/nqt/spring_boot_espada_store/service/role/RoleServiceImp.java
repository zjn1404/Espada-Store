package com.nqt.spring_boot_espada_store.service.role;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nqt.spring_boot_espada_store.dto.request.RoleRequest;
import com.nqt.spring_boot_espada_store.dto.response.RoleResponse;
import com.nqt.spring_boot_espada_store.entity.Permission;
import com.nqt.spring_boot_espada_store.entity.Role;
import com.nqt.spring_boot_espada_store.mapper.RoleMapper;
import com.nqt.spring_boot_espada_store.repository.PermissionRepository;
import com.nqt.spring_boot_espada_store.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImp implements RoleService {

    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        Role role = roleMapper.toRole(request);
        if (request.getPermissions() != null) {
            List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());

            role.setPermissions(new HashSet<>(permissions));
        }

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public void deleteById(String name) {
        roleRepository.deleteById(name);
    }
}
