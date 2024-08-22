package com.nqt.spring_boot_espada_store.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {

    String productId;

    String size;

    int quantity;
}
