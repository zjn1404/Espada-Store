package com.nqt.spring_boot_espada_store.dto.request.product;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductUpdateRequest {

    @Min(0)
    double price;

    @Min(0)
    int stock;

    @Lob
    MultipartFile image;

}
