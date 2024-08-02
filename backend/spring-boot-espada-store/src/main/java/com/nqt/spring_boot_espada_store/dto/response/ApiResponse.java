package com.nqt.spring_boot_espada_store.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    int code = 1000;
    String message;
    T result;

    public ApiResponse(String message, T result) {
        this.message = message;
        this.result = result;
    }

    public ApiResponse(T result) {
        this.result = result;
    }

    public ApiResponse(String message) {
        this.message = message;
    }
}
