package com.nqt.spring_boot_espada_store.dto.response;

import java.util.Set;

import com.nqt.spring_boot_espada_store.entity.Role;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    String id;
    String username;
    String password;
    String email;
    String phoneNumber;
    String firstName;
    String lastName;
    Set<Role> roles;
}
