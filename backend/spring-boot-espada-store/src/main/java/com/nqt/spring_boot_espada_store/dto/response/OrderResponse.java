package com.nqt.spring_boot_espada_store.dto.response;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {

    String id;

    String deliveryAddress;

    String phoneNumber;
    ;

    String state;

    double payment;

    boolean paymentState;

    Date orderingDate;

    Date shippingDate;

    String userId;
}
