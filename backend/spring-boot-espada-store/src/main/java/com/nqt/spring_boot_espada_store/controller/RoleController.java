package com.nqt.spring_boot_espada_store.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.nqt.spring_boot_espada_store.dto.request.RoleRequest;
import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.RoleResponse;
import com.nqt.spring_boot_espada_store.service.role.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Role Controller")
public class RoleController {

    RoleService roleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create role", description = "API creates role. Only admin can use this API!")
    public ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        ApiResponse<RoleResponse> apiResponse = new ApiResponse<>();
        RoleResponse roleResponse = roleService.create(request);

        apiResponse.setResult(roleResponse);

        return apiResponse;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Get all roles", description = "API gets all roles. Only admin can use this API!")
    public ApiResponse<List<RoleResponse>> getAll() {
        ApiResponse<List<RoleResponse>> apiResponse = new ApiResponse<>();
        List<RoleResponse> roles = roleService.getAll();

        apiResponse.setResult(roles);

        return apiResponse;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{name}")
    @Operation(summary = "Delete role by name", description = "API deletes role by name. Only admin can use this API!")
    public ApiResponse<Void> delete(@PathVariable("name") String name) {
        roleService.deleteById(name);

        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Role deleted!");

        return apiResponse;
    }
}
