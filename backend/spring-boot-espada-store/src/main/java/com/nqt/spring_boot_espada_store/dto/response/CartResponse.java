package com.nqt.spring_boot_espada_store.dto.response;

import java.util.Set;

import com.nqt.spring_boot_espada_store.entity.CartDetail;
import com.nqt.spring_boot_espada_store.entity.Product;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {

    Product product;

    Set<CartDetail> cartDetails;
}
