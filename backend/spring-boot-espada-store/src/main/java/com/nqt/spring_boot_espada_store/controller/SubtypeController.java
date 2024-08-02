package com.nqt.spring_boot_espada_store.controller;

import com.nqt.spring_boot_espada_store.dto.request.SubtypeRequest;
import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.SubtypeResponse;
import com.nqt.spring_boot_espada_store.service.subtype.SubtypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subtype")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubtypeController {

    SubtypeService subtypeService;

    @PostMapping
    public ApiResponse<SubtypeResponse> create(@RequestBody SubtypeRequest subtypeRequest) {
        return new ApiResponse<>(subtypeService.create(subtypeRequest));
    }

//    @GetMapping
//    public ApiResponse<SubtypeResponse> getById(@RequestParam("id") String id) {
//        return new ApiResponse<>(subtypeService.getById(id));
//    }

    @GetMapping
    public ApiResponse<List<SubtypeResponse>> getAll() {
        return new ApiResponse<>(subtypeService.getAllSubtypes());
    }

    @GetMapping("/by-type")
    public ApiResponse<List<SubtypeResponse>> getByType(@RequestParam("type") String type) {
        return new ApiResponse<>(subtypeService.getAllSubtypesByType(type));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable("id") String id) {
        subtypeService.delete(id);

        return new ApiResponse<>("Subtype deleted successfully");
    }
}
