package com.nqt.spring_boot_espada_store.controller;

import com.nqt.spring_boot_espada_store.dto.request.product.ProductCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.product.ProductUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.ProductResponse;
import com.nqt.spring_boot_espada_store.service.product.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductService productService;

    @PostMapping
    public ApiResponse<ProductResponse> create(@ModelAttribute ProductCreationRequest request) {
        return new ApiResponse<>(productService.createProduct(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> update(@PathVariable("id") String id, @RequestBody ProductUpdateRequest request) {
        return new ApiResponse<>(productService.updateProduct(id, request));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getById(@PathVariable("id") String id) {
        return new ApiResponse<>(productService.getProduct(id));
    }

    @GetMapping
    public ApiResponse<List<ProductResponse>> getAll() {
        return new ApiResponse<>(productService.getAllProducts());
    }

    @GetMapping("/{type}")
    public ApiResponse<List<ProductResponse>> getAllByType(@PathVariable("type") String type) {
        return new ApiResponse<>(productService.getProductsByType(type));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable("id") String id) {
        productService.deleteProduct(id);

        return new ApiResponse<>("Product deleted successfully");
    }
}
