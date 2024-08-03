package com.nqt.spring_boot_espada_store.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppException extends RuntimeException {
    final ErrorCode errorCode;
}
