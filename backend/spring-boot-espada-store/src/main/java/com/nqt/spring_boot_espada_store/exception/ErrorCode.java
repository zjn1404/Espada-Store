package com.nqt.spring_boot_espada_store.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Undefined error
    UNCATEGORIZED_EXCEPTION(9999, HttpStatus.INTERNAL_SERVER_ERROR, "Uncategorized Error"),
    // Developer error
    INVALID_KEY(1001, HttpStatus.INTERNAL_SERVER_ERROR, "Uncategorized error"),
    // Client error
    USER_EXISTED(1002, HttpStatus.BAD_REQUEST, "User Existed"),
    USERNAME_INVALID(1003, HttpStatus.BAD_REQUEST, "Username must be at least 4 characters"),
    PASSWORD_INVALID(1004, HttpStatus.BAD_REQUEST, "Password must be at least 8 characters"),
    USER_NOT_EXISTED(1005, HttpStatus.BAD_REQUEST, "User Not Existed"),
    UNAUTHENTICATED(1006, HttpStatus.UNAUTHORIZED, "Authentication Failed"),
    UNAUTHORIZED(1007, HttpStatus.FORBIDDEN, "Don't have permission"),
    INVALID_TOKEN(1008, HttpStatus.BAD_REQUEST, "Invalid Token"),
    TYPE_NOT_EXISTED(1009, HttpStatus.BAD_REQUEST, "Type Not Existed"),
    SUBTYPE_NOT_EXISTED(1010, HttpStatus.BAD_REQUEST, "SubType Not Existed"),
    PRODUCT_NOT_EXISTED(1011, HttpStatus.BAD_REQUEST, "Product Not Existed"),
    TYPE_EXISTED(1012, HttpStatus.BAD_REQUEST, "Type Existed"),
    SUB_TYPE_EXISTED(1013, HttpStatus.BAD_REQUEST, "Sub Type Existed"),
    PRODUCT_EXISTED(1014, HttpStatus.BAD_REQUEST, "Product Existed"),
    ROLE_NOT_EXISTED(1015, HttpStatus.BAD_REQUEST, "Role Not Existed"),
    ;

    final int code;
    final HttpStatus httpStatus;
    final String message;
}
