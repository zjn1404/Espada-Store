package com.nqt.spring_boot_espada_store.controller;

import java.util.List;

import com.nqt.spring_boot_espada_store.service.permission.PermissionService;
import org.springframework.web.bind.annotation.*;

import com.nqt.spring_boot_espada_store.dto.request.PermissionRequest;
import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.PermissionResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {

    PermissionService permissionService;

    @PostMapping
    public ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        ApiResponse<PermissionResponse> apiResponse = new ApiResponse<>();
        PermissionResponse permissionResponse = permissionService.create(request);

        apiResponse.setResult(permissionResponse);

        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getAll() {
        ApiResponse<List<PermissionResponse>> apiResponse = new ApiResponse<>();
        List<PermissionResponse> permissionResponses = permissionService.getAll();

        apiResponse.setResult(permissionResponses);

        return apiResponse;
    }

    @DeleteMapping("/{name}")
    public ApiResponse<Void> deletePermission(@PathVariable("name") String name) {
        permissionService.deleteById(name);
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Permission deleted!");
        return apiResponse;
    }
}
