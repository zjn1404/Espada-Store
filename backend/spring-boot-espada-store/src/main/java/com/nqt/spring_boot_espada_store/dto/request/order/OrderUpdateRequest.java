package com.nqt.spring_boot_espada_store.dto.request.order;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderUpdateRequest {

    String state;

    Boolean paymentState;

    Date shippingDate;
}
