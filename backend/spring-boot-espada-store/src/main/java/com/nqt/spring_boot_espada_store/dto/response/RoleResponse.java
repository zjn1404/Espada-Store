package com.nqt.spring_boot_espada_store.dto.response;

import java.util.Set;

import com.nqt.spring_boot_espada_store.entity.Permission;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {
    String name;
    String description;
    Set<Permission> permissions;
}
