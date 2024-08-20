package com.nqt.spring_boot_espada_store.controller;

import com.nqt.spring_boot_espada_store.dto.request.TypeRequest;
import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.TypeResponse;
import com.nqt.spring_boot_espada_store.service.type.TypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Type Controller")
public class TypeController {

    TypeService typeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create product type", description = "API creates product type. Only admin can use this api!")
    public ApiResponse<TypeResponse> create(@RequestBody TypeRequest request) {
        TypeResponse typeResponse = typeService.createType(request);

        ApiResponse<TypeResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(typeResponse);

        return apiResponse;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product type by ID (type's name)", description = "API gets product type by its name.")
    public ApiResponse<TypeResponse> getById(@PathVariable("id") String id) {
        TypeResponse typeResponse = typeService.getTypeById(id);

        return new ApiResponse<>(typeResponse);
    }

    @GetMapping
    @Operation(summary = "Get all product types", description = "API gets all product type.")
    public ApiResponse<List<TypeResponse>> getAll() {
        return new ApiResponse<>(typeService.getAllTypes());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product type by ID (type's name)", description = "API deletes product type by its name. Only admin can use this api!")
    public ApiResponse<Void> delete(@PathVariable("id") String id) {
        typeService.deleteTypeById(id);

        return new ApiResponse<>("Type deleted successfully");
    }
}
