package com.nqt.spring_boot_espada_store.dto.request.product;

import jakarta.validation.constraints.Min;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreationRequest {

    String name;

    @Min(0)
    double price;

    String color;

    String material;

    String size;

    String gender;

    String description;

    @Min(0)
    int stock;

    MultipartFile image;

    String subtype;

    String form;
}
