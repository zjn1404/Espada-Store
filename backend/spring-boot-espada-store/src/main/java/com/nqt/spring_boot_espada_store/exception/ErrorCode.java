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
    CUSTOMER_DETAIL_EXISTED(1016, HttpStatus.BAD_REQUEST, "Customer Detail Existed"),
    CUSTOMER_DETAIL_NOT_EXISTED(1017, HttpStatus.BAD_REQUEST, "Customer Detail Not Existed"),
    USER_NOT_VERIFIED(1018, HttpStatus.BAD_REQUEST, "User Not Verified"),
    VERIFY_CODE_INCORRECT(1019, HttpStatus.BAD_REQUEST, "Verify Code Incorrect"),
    VERIFY_CODE_EXPIRED(1020, HttpStatus.BAD_REQUEST, "Verify Code Expired, New Code Has Been Sent"),
    ACCOUNT_NOT_VERIFIED(1021, HttpStatus.BAD_REQUEST, "Account Not Verified"),
    PASSWORD_INCORRECT(1022, HttpStatus.BAD_REQUEST, "Password Incorrect"),
    THE_SAME_PASSWORD(1023, HttpStatus.BAD_REQUEST, "New Password Is The Same As Old Password"),
    QUANTITY_INVALID(1024, HttpStatus.BAD_REQUEST, "Quantity MUST BE AT LEAST 1"),
    PAYMENT_INVALID(1025, HttpStatus.BAD_REQUEST, "Payment MUST BE AT LEAST 0"),
    CART_NOT_EXISTED(1026, HttpStatus.BAD_REQUEST, "Cart Not Existed"),
    CART_DETAIL_NOT_EXISTED(1027, HttpStatus.BAD_REQUEST, "Cart Detail Not Existed"),
    ORDER_NOT_EXISTED(1028, HttpStatus.BAD_REQUEST, "Order Not Existed"),
    QUANTITY_GREATER_THAN_STOCK(1029, HttpStatus.BAD_REQUEST, "Quantity is greater than stock"),
    PAYMENT_NOT_SUCCEED(1030, HttpStatus.BAD_REQUEST, "Payment Not Succeed"),
    ;

    final int code;
    final HttpStatus httpStatus;
    final String message;
}
