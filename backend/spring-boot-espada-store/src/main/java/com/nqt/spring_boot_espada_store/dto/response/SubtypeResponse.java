package com.nqt.spring_boot_espada_store.dto.response;

import com.nqt.spring_boot_espada_store.entity.Type;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubtypeResponse {

    String name;

    Type type;

}
