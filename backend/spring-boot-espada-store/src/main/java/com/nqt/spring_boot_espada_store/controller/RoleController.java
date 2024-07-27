package com.nqt.spring_boot_espada_store.controller;

import com.nqt.spring_boot_espada_store.dto.request.RoleRequest;
import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.RoleResponse;
import com.nqt.spring_boot_espada_store.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleService roleService;

    @PostMapping
    public ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        ApiResponse<RoleResponse> apiResponse = new ApiResponse<>();
        RoleResponse roleResponse = roleService.create(request);

        apiResponse.setResult(roleResponse);

        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAll() {
        ApiResponse<List<RoleResponse>> apiResponse = new ApiResponse<>();
        List<RoleResponse> roles = roleService.getAll();

        apiResponse.setResult(roles);

        return apiResponse;
    }

    @DeleteMapping("/{name}")
    public ApiResponse<?> delete(@PathVariable("name") String name) {
        roleService.deleteById(name);

        ApiResponse<RoleResponse> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Role deleted!");

        return apiResponse;
    }
}
