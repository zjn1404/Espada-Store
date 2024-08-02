package com.nqt.spring_boot_espada_store.dto.request.product;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreationRequest {

    String name;

    double price;

    String color;

    String material;

    String size;

    String gender;

    String description;

    int stock;

    MultipartFile image;

    String subtype;
}
