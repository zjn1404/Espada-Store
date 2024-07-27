package com.nqt.spring_boot_espada_store.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Undefined error
    UNCATEGORIZED_EXCEPTION(9999, HttpStatus.INTERNAL_SERVER_ERROR , "Uncategorized Error"),
    // Developer error
    INVALID_KEY(1001, HttpStatus.INTERNAL_SERVER_ERROR, "Uncategorized error"),
    // Client error
    USER_EXISTED(1002, HttpStatus.BAD_REQUEST, "User Existed"),
    USERNAME_INVALID(1003, HttpStatus.BAD_REQUEST, "Username must be at least 4 characters"),
    PASSWORD_INVALID(1004, HttpStatus.BAD_REQUEST, "Password must be at least 8 characters"),
    USER_NOT_EXISTED(1005, HttpStatus.BAD_REQUEST, "User Not Existed"),
    UNAUTHENTICATED(1006, HttpStatus.UNAUTHORIZED, "Authentication Failed"),
    UNAUTHORIZED(1007, HttpStatus.FORBIDDEN, "Don't have permission"),
    INVALID_TOKEN(1008, HttpStatus.INTERNAL_SERVER_ERROR, "Invalid Token"),
    ;

    final int code;
    final HttpStatus httpStatus;
    final String message;
}
