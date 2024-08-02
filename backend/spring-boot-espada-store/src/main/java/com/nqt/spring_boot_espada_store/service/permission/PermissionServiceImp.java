package com.nqt.spring_boot_espada_store.service.permission;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nqt.spring_boot_espada_store.dto.request.PermissionRequest;
import com.nqt.spring_boot_espada_store.dto.response.PermissionResponse;
import com.nqt.spring_boot_espada_store.entity.Permission;
import com.nqt.spring_boot_espada_store.mapper.PermissionMapper;
import com.nqt.spring_boot_espada_store.repository.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImp implements PermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);

        permissionRepository.save(permission);

        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }

    public void deleteById(String name) {
        permissionRepository.deleteById(name);
    }
}
