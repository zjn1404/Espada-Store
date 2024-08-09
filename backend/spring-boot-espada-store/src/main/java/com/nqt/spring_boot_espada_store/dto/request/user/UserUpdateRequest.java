package com.nqt.spring_boot_espada_store.dto.request.user;

import java.util.Set;

import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    @Size(min = 6, message = "PASSWORD_INVALID")
    String password;

    String email;
    String phoneNumber;
    String firstName;
    String lastName;
    Set<String> roles;
}
