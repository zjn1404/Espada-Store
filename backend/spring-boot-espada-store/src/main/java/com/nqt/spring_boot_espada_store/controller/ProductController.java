package com.nqt.spring_boot_espada_store.controller;

import com.nqt.spring_boot_espada_store.dto.request.product.ProductCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.product.ProductUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.ProductResponse;
import com.nqt.spring_boot_espada_store.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@RestController
@RequestMapping("/product")
// Make returned json page stable
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    @Value("${page-size-default.product}")
    @NonFinal
    Integer PAGE_SIZE;

    ProductService productService;

    @PostMapping
    public ApiResponse<ProductResponse> create(@ModelAttribute @Valid ProductCreationRequest request) {
        return new ApiResponse<>(productService.createProduct(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> update(@PathVariable("id") String id, @ModelAttribute @Valid ProductUpdateRequest request) {
        return new ApiResponse<>(productService.updateProduct(id, request));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getById(@PathVariable("id") String id) {
        return new ApiResponse<>(productService.getProduct(id));
    }

    // min page = 1
    @GetMapping
    public ApiResponse<Page<ProductResponse>> getAll(@RequestParam(value = "page", required = false) Integer offset,
                                                     @RequestParam(value = "size", required = false) Integer pageSize,
                                                     @RequestParam(value = "sort", required = false) String sortBy) {

        if(null == offset) offset = 0;
        if(null == pageSize) pageSize = PAGE_SIZE;
        if(StringUtils.isEmpty(sortBy)) sortBy ="id";

        return new ApiResponse<>(productService.getAllProducts(PageRequest.of(offset, pageSize, Sort.by(sortBy))));
    }

    @GetMapping("/type/{type}")
    public ApiResponse<List<ProductResponse>> getAllByType(@PathVariable("type") String type) {
        return new ApiResponse<>(productService.getProductsByType(type));
    }

    @GetMapping("/subtype/{subtype}")
    public ApiResponse<Page<ProductResponse>> getAllBySubtype(@PathVariable("subtype") String subtype,
                                                              @RequestParam(value = "page", required = false) Integer offset,
                                                              @RequestParam(value = "size", required = false) Integer pageSize,
                                                              @RequestParam(value = "sort", required = false) String sortBy
                                                              ) {
        if(null == offset) offset = 0;
        if(null == pageSize) pageSize = PAGE_SIZE;
        if(StringUtils.isEmpty(sortBy)) sortBy ="id";

        return new ApiResponse<>(productService.getProductsBySubtype(subtype, PageRequest.of(offset, pageSize, Sort.by(sortBy))));
    }

    @GetMapping("/search")
    public ApiResponse<Page<ProductResponse>> getAllBySearch(@RequestParam("input") String input,
                                                             @RequestParam(value = "page", required = false) Integer offset,
                                                             @RequestParam(value = "size", required = false) Integer pageSize,
                                                             @RequestParam(value = "sort", required = false) String sortBy
                                                            ) {
        if(null == offset) offset = 0;
        if(null == pageSize) pageSize = PAGE_SIZE;
        if(StringUtils.isEmpty(sortBy)) sortBy ="id";

        return new ApiResponse<>(productService.getProductsBySearch(input, PageRequest.of(offset, pageSize, Sort.by(sortBy))));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> delete(@PathVariable("id") String id) {
        productService.deleteProduct(id);

        return new ApiResponse<>("Product deleted successfully");
    }
}
