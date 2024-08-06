package com.nqt.spring_boot_espada_store.dto.response;

import com.nqt.spring_boot_espada_store.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDetailResponse {

    String gender;

    String address;

    String deliveryAddress;

    Date dob;

    boolean registerToGetMail;

}
