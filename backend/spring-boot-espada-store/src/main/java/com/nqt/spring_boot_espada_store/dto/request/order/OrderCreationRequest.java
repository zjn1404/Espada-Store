package com.nqt.spring_boot_espada_store.dto.request.order;

import java.util.Map;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreationRequest {

    String deliveryAddress;

    @Size(min = 10, max = 10)
    String phoneNumber;

    @Min(value = 0, message = "PAYMENT_INVALID")
    Double payment;

    Boolean paymentState;

    //    OrderDetail
    Map<String, Map<String, Integer>> productSizeQuantity; // maybe can't be mapped
}
