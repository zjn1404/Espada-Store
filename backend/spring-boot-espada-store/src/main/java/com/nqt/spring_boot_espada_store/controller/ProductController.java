package com.nqt.spring_boot_espada_store.controller;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

import java.util.List;

import jakarta.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.nqt.spring_boot_espada_store.dto.request.product.ProductCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.product.ProductUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.ProductResponse;
import com.nqt.spring_boot_espada_store.service.product.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@RestController
@RequestMapping("/product")
// Make returned json page stable
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Product Controller")
public class ProductController {

    @Value("${page-size-default.product}")
    @NonFinal
    Integer PAGE_SIZE;

    ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create product", description = "API creates product. Only admin can use this API!")
    public ApiResponse<ProductResponse> create(@ModelAttribute @Valid ProductCreationRequest request) {
        return new ApiResponse<>(productService.createProduct(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update product", description = "API creates product. Only admin can use this API!")
    public ApiResponse<ProductResponse> update(
            @PathVariable("id") String id, @ModelAttribute @Valid ProductUpdateRequest request) {
        return new ApiResponse<>(productService.updateProduct(id, request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "API gets product by ID.")
    public ApiResponse<ProductResponse> getById(@PathVariable("id") String id) {
        return new ApiResponse<>(productService.getProduct(id));
    }

    // min page = 1
    @GetMapping
    @Operation(summary = "Get all products", description = "API gets all products.")
    @ApiResponses(
            value = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Successfully retrieved products",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = ApiResponse.class),
                                        examples =
                                                @ExampleObject(
                                                        value =
                                                                """
									{
									"code": 0,
									"message": "string",
									"result": {
										"content": [
										{
											"id": "string",
											"name": "string",
											"price": 0,
											"color": "string",
											"material": "string",
											"size": "string",
											"form": "string",
											"gender": "string",
											"description": "string",
											"stock": 0,
											"image": "string",
											"subtype": {
											"name": "string",
											"type": {
												"name": "string"
											}
											}
										}
										],
										"page": {
										"size": "string",
										"number": 0,
										"totalElements": 0,
										"totalPages": 0
										}
									}
									}
									"""))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "400",
                        description = "Invalid request parameters",
                        content = @Content)
            })
    public ApiResponse<Page<ProductResponse>> getAll(
            @RequestParam(value = "page", required = false) Integer offset,
            @RequestParam(value = "size", required = false) Integer pageSize,
            @RequestParam(value = "sort", required = false) String sortBy) {

        if (null == offset) offset = 0;
        if (null == pageSize) pageSize = PAGE_SIZE;
        if (StringUtils.isEmpty(sortBy)) sortBy = "id";

        return new ApiResponse<>(productService.getAllProducts(PageRequest.of(offset, pageSize, Sort.by(sortBy))));
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Get all product by type", description = "API gets all products by type.")
    public ApiResponse<List<ProductResponse>> getAllByType(@PathVariable("type") String type) {
        return new ApiResponse<>(productService.getProductsByType(type));
    }

    @GetMapping("/subtype/{subtype}")
    @Operation(summary = "Get all product by subtype", description = "API gets all products by subtype.")
    public ApiResponse<Page<ProductResponse>> getAllBySubtype(
            @PathVariable("subtype") String subtype,
            @RequestParam(value = "page", required = false) Integer offset,
            @RequestParam(value = "size", required = false) Integer pageSize,
            @RequestParam(value = "sort", required = false) String sortBy) {
        if (null == offset) offset = 0;
        if (null == pageSize) pageSize = PAGE_SIZE;
        if (StringUtils.isEmpty(sortBy)) sortBy = "id";

        return new ApiResponse<>(
                productService.getProductsBySubtype(subtype, PageRequest.of(offset, pageSize, Sort.by(sortBy))));
    }

    @GetMapping("/search")
    @Operation(summary = "Get products by keyword", description = "API gets products by keyword.")
    public ApiResponse<Page<ProductResponse>> getAllBySearch(
            @RequestParam("input") String input,
            @RequestParam(value = "page", required = false) Integer offset,
            @RequestParam(value = "size", required = false) Integer pageSize,
            @RequestParam(value = "sort", required = false) String sortBy) {
        if (null == offset) offset = 0;
        if (null == pageSize) pageSize = PAGE_SIZE;
        if (StringUtils.isEmpty(sortBy)) sortBy = "id";

        return new ApiResponse<>(
                productService.getProductsBySearch(input, PageRequest.of(offset, pageSize, Sort.by(sortBy))));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete product by ID",
            description = "API deletes product by ID. Only admin can use this API!")
    public ApiResponse<Void> delete(@PathVariable("id") String id) {
        productService.deleteProduct(id);

        return new ApiResponse<>("Product deleted successfully");
    }
}
