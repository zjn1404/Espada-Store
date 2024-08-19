package com.nqt.spring_boot_espada_store.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Undefined error
    UNCATEGORIZED_EXCEPTION(9999, HttpStatus.INTERNAL_SERVER_ERROR, "Uncategorized Error"),

    // Developer error (1xxx)
    INVALID_KEY(1001, HttpStatus.INTERNAL_SERVER_ERROR, "Uncategorized error"),

    // Client error
    // Input error (2xxx)
    USERNAME_INVALID(2001, HttpStatus.BAD_REQUEST, "Username must be at least 4 characters"),
    PASSWORD_INVALID(2002, HttpStatus.BAD_REQUEST, "Password must be at least 8 characters"),
    THE_SAME_PASSWORD(2003, HttpStatus.BAD_REQUEST, "New Password Is The Same As Old Password"),
    PAYMENT_INVALID(2004, HttpStatus.BAD_REQUEST, "Payment MUST BE AT LEAST 0"),
    QUANTITY_INVALID(2005, HttpStatus.BAD_REQUEST, "Quantity MUST BE AT LEAST 1"),
    QUANTITY_GREATER_THAN_STOCK(2006, HttpStatus.BAD_REQUEST, "Quantity is greater than stock"),
    VERIFY_CODE_INCORRECT(2007, HttpStatus.BAD_REQUEST, "Verify Code Incorrect"),
    // Existed error (2xxx)
    USER_EXISTED(2008, HttpStatus.BAD_REQUEST, "User Existed"),
    TYPE_EXISTED(2009, HttpStatus.BAD_REQUEST, "Type Existed"),
    SUB_TYPE_EXISTED(2010, HttpStatus.BAD_REQUEST, "Sub Type Existed"),
    PRODUCT_EXISTED(2011, HttpStatus.BAD_REQUEST, "Product Existed"),
    CUSTOMER_DETAIL_EXISTED(2012, HttpStatus.BAD_REQUEST, "Customer Detail Existed"),
    VERIFY_CODE_EXPIRED(2013, HttpStatus.BAD_REQUEST, "Verify Code Expired, New Code Has Been Sent"),
    // Other (2xxx)
    PAYMENT_NOT_SUCCEED(2014, HttpStatus.BAD_REQUEST, "Payment Not Succeed"),
    // Unauthenticated error (3xxx)
    UNAUTHENTICATED(3001, HttpStatus.UNAUTHORIZED, "Authentication Failed"),
    INVALID_TOKEN(3002, HttpStatus.UNAUTHORIZED, "Invalid Token"),
    ACCOUNT_NOT_VERIFIED(3003, HttpStatus.UNAUTHORIZED, "Account Not Verified"),
    PASSWORD_INCORRECT(3004, HttpStatus.UNAUTHORIZED, "Password Incorrect"),
    // Unauthorized error (4xxx)
    UNAUTHORIZED(4001, HttpStatus.FORBIDDEN, "Don't have permission"),
    // Not Found error (5xxx)
    USER_NOT_EXISTED(5001, HttpStatus.NOT_FOUND, "User Not Existed"),
    TYPE_NOT_EXISTED(5002, HttpStatus.NOT_FOUND, "Type Not Existed"),
    SUBTYPE_NOT_EXISTED(5003, HttpStatus.NOT_FOUND, "SubType Not Existed"),
    PRODUCT_NOT_EXISTED(5004, HttpStatus.NOT_FOUND, "Product Not Existed"),
    ROLE_NOT_EXISTED(5005, HttpStatus.NOT_FOUND, "Role Not Existed"),
    CUSTOMER_DETAIL_NOT_EXISTED(5006, HttpStatus.NOT_FOUND, "Customer Detail Not Existed"),
    CART_NOT_EXISTED(5007, HttpStatus.NOT_FOUND, "Cart Not Existed"),
    CART_DETAIL_NOT_EXISTED(5008, HttpStatus.NOT_FOUND, "Cart Detail Not Existed"),
    ORDER_NOT_EXISTED(5009, HttpStatus.NOT_FOUND, "Order Not Existed"),
    ;

    final int code;
    final HttpStatus httpStatus;
    final String message;
}
