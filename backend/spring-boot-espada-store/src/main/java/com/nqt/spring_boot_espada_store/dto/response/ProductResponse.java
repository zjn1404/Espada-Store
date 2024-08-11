package com.nqt.spring_boot_espada_store.dto.response;

import com.nqt.spring_boot_espada_store.entity.Subtype;
import jakarta.persistence.Lob;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {

    String id;

    String name;

    double price;

    String color;

    String material;

    String size;

    String form;

    String gender;

    String description;

    int stock;

    @Lob
    String image;

    Subtype subtype;

}
