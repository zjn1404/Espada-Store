package com.nqt.spring_boot_espada_store.dto.request.customer;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerCreationRequest {

    @Size(min = 4, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    String email;
    String phoneNumber;
    String firstName;
    String lastName;
    String gender;
    String address;
    String deliveryAddress;
    Date dob;
    boolean registerToGetMail;

    final Set<String> roles = new HashSet<>(List.of("USER"));
}
