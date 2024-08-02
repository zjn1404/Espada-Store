package com.nqt.spring_boot_espada_store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @Column(name = "product_id")
    String id;

    @Column(name = "product_name", unique = true, nullable = false)
    String name;

    @Column(name = "price")
    double price;

    @Column(name = "color")
    String color;

    @Column(name = "material")
    String material;

    @Column(name = "size")
    String size;

    @Column(name = "gender")
    String gender;

    @Column(name = "product_description")
    String description;

    @Column(name = "stock")
    int stock;

    @Column(name = "img")
    @Lob
    byte[] image;

    @OneToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    Subtype subtype;
}
