package com.nqt.spring_boot_espada_store.controller;

import com.nqt.spring_boot_espada_store.dto.request.SubtypeRequest;
import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.SubtypeResponse;
import com.nqt.spring_boot_espada_store.service.subtype.SubtypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subtype")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Subtype Controller")
public class SubtypeController {

    SubtypeService subtypeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create product subtype", description = "API creates product subtype. Only admin can use this API!")
    public ApiResponse<SubtypeResponse> create(@RequestBody SubtypeRequest subtypeRequest) {
        return new ApiResponse<>(subtypeService.create(subtypeRequest));
    }

//    @GetMapping
//    public ApiResponse<SubtypeResponse> getById(@RequestParam("id") String id) {
//        return new ApiResponse<>(subtypeService.getById(id));
//    }

    @GetMapping
    @Operation(summary = "Get all product subtypes", description = "API gets all product subtypes.")
    public ApiResponse<List<SubtypeResponse>> getAll() {
        return new ApiResponse<>(subtypeService.getAllSubtypes());
    }

    @GetMapping("/by-type")
    @Operation(summary = "Get all product subtypes by type", description = "API gets all product subtypes by type.")
    public ApiResponse<List<SubtypeResponse>> getByType(@RequestParam("type") String type) {
        return new ApiResponse<>(subtypeService.getAllSubtypesByType(type));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product subtype by ID", description = "API delete product subtype by ID. Only admin can use this API!")
    public ApiResponse<Void> delete(@PathVariable("id") String id) {
        subtypeService.delete(id);

        return new ApiResponse<>("Subtype deleted successfully");
    }
}
