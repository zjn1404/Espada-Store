package com.nqt.spring_boot_espada_store.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.nqt.spring_boot_espada_store.dto.request.PermissionRequest;
import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.PermissionResponse;
import com.nqt.spring_boot_espada_store.service.permission.PermissionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Permission Controller")
public class PermissionController {

    PermissionService permissionService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create permission", description = "API creates permission. Only admin can use this API!")
    public ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        ApiResponse<PermissionResponse> apiResponse = new ApiResponse<>();
        PermissionResponse permissionResponse = permissionService.create(request);

        apiResponse.setResult(permissionResponse);

        return apiResponse;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Get all permissions", description = "API gets all permissions. Only admin can use this API!")
    public ApiResponse<List<PermissionResponse>> getAll() {
        ApiResponse<List<PermissionResponse>> apiResponse = new ApiResponse<>();
        List<PermissionResponse> permissionResponses = permissionService.getAll();

        apiResponse.setResult(permissionResponses);

        return apiResponse;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{name}")
    @Operation(
            summary = "Delete permission by name",
            description = "API deletes permission by name. Only admin can use this API!")
    public ApiResponse<Void> deletePermission(@PathVariable("name") String name) {
        permissionService.deleteById(name);
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Permission deleted!");
        return apiResponse;
    }
}
