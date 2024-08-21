package com.nqt.spring_boot_espada_store.service.role;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.nqt.spring_boot_espada_store.dto.request.RoleRequest;
import com.nqt.spring_boot_espada_store.dto.response.RoleResponse;

public interface RoleService {

    @PreAuthorize("hasRole('ADMIN')")
    RoleResponse create(RoleRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    List<RoleResponse> getAll();

    @PreAuthorize("hasRole('ADMIN')")
    void deleteById(String name);
}
