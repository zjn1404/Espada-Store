package com.nqt.spring_boot_espada_store.service.product;

import com.nqt.spring_boot_espada_store.dto.request.TypeRequest;
import com.nqt.spring_boot_espada_store.dto.request.product.ProductCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.product.ProductUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductCreationRequest request);

    ProductResponse updateProduct(String id, ProductUpdateRequest request);

    ProductResponse getProduct(String id);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> getProductsByType(String type);

    void deleteProduct(String id);
}
