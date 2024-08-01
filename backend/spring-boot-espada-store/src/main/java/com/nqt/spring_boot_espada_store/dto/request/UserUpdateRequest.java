package com.nqt.spring_boot_espada_store.dto.request;

import java.util.Set;

import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
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
    boolean enabled;
    Set<String> roles;
}
