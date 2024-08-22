package com.nqt.spring_boot_espada_store.dto.request.customer;

import java.util.Date;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDetailUpdateRequest {

    String gender;
    String address;
    String deliveryAddress;
    Date dob;
    boolean registerToGetMail;
}
