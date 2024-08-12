package com.nqt.spring_boot_espada_store.service.product;

import com.nqt.spring_boot_espada_store.dto.request.product.ProductCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.product.ProductUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.ProductResponse;
import com.nqt.spring_boot_espada_store.entity.Product;
import com.nqt.spring_boot_espada_store.entity.Subtype;
import com.nqt.spring_boot_espada_store.entity.Type;
import com.nqt.spring_boot_espada_store.exception.AppException;
import com.nqt.spring_boot_espada_store.exception.ErrorCode;
import com.nqt.spring_boot_espada_store.mapper.ProductMapper;
import com.nqt.spring_boot_espada_store.repository.ProductRepository;
import com.nqt.spring_boot_espada_store.repository.SubtypeRepository;
import com.nqt.spring_boot_espada_store.repository.TypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImp implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImp.class);
    ProductRepository productRepository;
    SubtypeRepository subtypeRepository;
    TypeRepository typeRepository;

    ProductMapper productMapper;


    @Override
    public ProductResponse createProduct(ProductCreationRequest request) {

        if (productRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }

        try {

            byte[] img = null;
            if (!Objects.isNull(request.getImage())) {
                img = request.getImage().getBytes();
            }

            Subtype subtype = subtypeRepository.findById(request.getSubtype())
                    .orElseThrow(() -> new AppException(ErrorCode.SUBTYPE_NOT_EXISTED));

            Product product = productMapper.toProduct(request);
            String base64Img = Base64.getEncoder().encodeToString(img);
            product.setImage(base64Img);
            product.setSubtype(subtype);

            String id = createId(product.getName(), product.getSubtype().getName(), product.getGender());
            product.setId(id);

            productRepository.save(product);
            return productMapper.toProductResponse(product);

        } catch (IOException ex) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    private String createId(String name, String subtype, String gender) {
        StringBuilder builder = new StringBuilder();
        String[] names = name.split(" ");
        builder.append(subtype.charAt(0));
        builder.append(gender.charAt(0));
        for (String str : names) {
            builder.append(str.charAt(0));
        }
        LocalDate addedDate = LocalDate.now();

        builder.append(String.format("%d-%d", addedDate.getMonthValue(), addedDate.getYear()));

        return builder.toString();
    }

    @Override
    public ProductResponse updateProduct(String id, ProductUpdateRequest request) {
        try {
            byte[] img = null;
            if (!Objects.isNull(request.getImage())) {
                img = request.getImage().getBytes();
            }

            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
            productMapper.updateProduct(product, request);

            String base64Img = Base64.getEncoder().encodeToString(img);
            product.setImage(base64Img);

            productRepository.saveAndFlush(product);

            return productMapper.toProductResponse(product);

        } catch (IOException ex) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @Override
    public ProductResponse getProduct(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        return productMapper.toProductResponse(product);
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest request) {
        return productRepository.findAll(request).map(productMapper::toProductResponse);
    }

    @Override
    public List<ProductResponse> getProductsByType(String type) {
        Type foundType = typeRepository.findById(type)
                .orElseThrow(() -> new AppException(ErrorCode.TYPE_NOT_EXISTED));

        return productRepository.findAllByType(foundType).stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> getProductsBySubtype(String subtype) {
        Subtype foundSubtype = subtypeRepository.findById(subtype)
                .orElseThrow(() -> new AppException(ErrorCode.SUBTYPE_NOT_EXISTED));

        return productRepository.findAllBySubtype(foundSubtype).stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    @Override
    public Page<ProductResponse> getProductsBySubtype(String subtype, PageRequest request) {
        Subtype foundSubtype = subtypeRepository.findById(subtype)
                .orElseThrow(() -> new AppException(ErrorCode.SUBTYPE_NOT_EXISTED));

        return productRepository.findAllBySubtype(foundSubtype, request).map(productMapper::toProductResponse);
    }

    @Override
    public Page<ProductResponse> getProductsBySearch(String input, PageRequest request) {
        Page<Product> page = productRepository.findProductsByNameContaining(input, request);

        if (page.getContent().isEmpty()) {
            page = productRepository.findProductsById((input), request);
        }

        return page.map(productMapper::toProductResponse);
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
