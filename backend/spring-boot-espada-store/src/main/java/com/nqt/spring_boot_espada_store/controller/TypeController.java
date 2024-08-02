package com.nqt.spring_boot_espada_store.controller;

import com.nqt.spring_boot_espada_store.dto.request.TypeRequest;
import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.TypeResponse;
import com.nqt.spring_boot_espada_store.service.type.TypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/type")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TypeController {

    TypeService typeService;

    @PostMapping
    public ApiResponse<TypeResponse> create(@RequestBody TypeRequest request) {
        TypeResponse typeResponse = typeService.createType(request);

        ApiResponse<TypeResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(typeResponse);

        return apiResponse;
    }

    @GetMapping("/{id}")
    public ApiResponse<TypeResponse> getById(@PathVariable("id") String id) {
        TypeResponse typeResponse = typeService.getTypeById(id);

        return new ApiResponse<>(typeResponse);
    }

    @GetMapping
    public ApiResponse<List<TypeResponse>> getAll() {
        return new ApiResponse<>(typeService.getAllTypes());
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable("id") String id) {
        typeService.deleteTypeById(id);

        return new ApiResponse<>("Type deleted successfully");
    }
}
