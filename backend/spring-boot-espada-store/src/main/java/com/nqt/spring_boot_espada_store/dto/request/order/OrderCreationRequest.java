package com.nqt.spring_boot_espada_store.dto.request.order;

import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreationRequest {

    String deliveryAddress;

    @Min(value = 0, message = "PAYMENT_INVALID")
    Double payment;

    Boolean paymentState;

//    OrderDetail
    Map<String, Map<String, Integer>> productSizeQuantity; // maybe can't be mapped
}
