package com.nqt.spring_boot_espada_store.dto.request.customer;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerUpdateRequest {

    // update user
    @Size(min = 6, message = "PASSWORD_INVALID")
    String password;

    String email;
    String phoneNumber;
    String firstName;
    String lastName;
    // TODO: Consider to delete
    boolean enabled;
    // end update user

    String gender;
    String address;
    String deliveryAddress;
    Date dob;
    boolean registerToGetMail;
}
