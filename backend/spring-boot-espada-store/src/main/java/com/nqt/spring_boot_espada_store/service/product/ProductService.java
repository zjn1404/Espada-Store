package com.nqt.spring_boot_espada_store.service.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.nqt.spring_boot_espada_store.dto.request.product.ProductCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.product.ProductUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(ProductCreationRequest request);

    ProductResponse updateProduct(String id, ProductUpdateRequest request);

    ProductResponse getProduct(String id);

    Page<ProductResponse> getAllProducts(PageRequest request);

    List<ProductResponse> getProductsByType(String type);

    List<ProductResponse> getProductsBySubtype(String subtype);

    Page<ProductResponse> getProductsBySubtype(String subtype, PageRequest request);

    Page<ProductResponse> getProductsBySearch(String input, PageRequest request);

    void deleteProduct(String id);
}
