package com.nqt.spring_boot_espada_store.dto.request.product;

import jakarta.annotation.Nullable;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductUpdateRequest {

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

    String form;

    @Lob
    @Nullable
    MultipartFile image;

}
