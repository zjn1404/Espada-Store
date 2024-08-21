package com.nqt.spring_boot_espada_store.controller;

import java.io.IOException;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.service.verifycode.VerifyCodeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@RestController
@RequestMapping("/verify")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Verify Code Controller")
public class VerifyCodeController {

    VerifyCodeService verifyCodeService;

    @NonFinal
    @Value("${success-request-code}")
    int SUCCESS_REQUEST_CODE;

    @NonFinal
    @Value("${client.verification-success-page}")
    String CLIENT_VERIFICATION_SUCCESS_PAGE;

    @GetMapping
    @Operation(
            summary = "Verify code",
            description = "API verifies code which is sent to user's email after registering a new account.")
    public ApiResponse<Object> verifyCode(
            @RequestParam("code") String code, @RequestParam("user") String userId, HttpServletResponse response)
            throws MessagingException, IOException {
        verifyCodeService.verify(code, userId);
        response.sendRedirect(CLIENT_VERIFICATION_SUCCESS_PAGE);
        return ApiResponse.builder()
                .code(SUCCESS_REQUEST_CODE)
                .message("Verify success")
                .build();
    }
}
