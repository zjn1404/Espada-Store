package com.nqt.spring_boot_espada_store.service.permission;

import com.nqt.spring_boot_espada_store.dto.request.PermissionRequest;
import com.nqt.spring_boot_espada_store.dto.response.PermissionResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface PermissionService {

    @PreAuthorize("hasRole('ADMIN')")
    PermissionResponse create(PermissionRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    List<PermissionResponse> getAll();

    @PreAuthorize("hasRole('ADMIN')")
    void deleteById(String name);
}
