package com.nqt.spring_boot_espada_store.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VNPAYResponse {
    String vnpCode;
    String vnpMessage;
    String paymentUrl;
}
