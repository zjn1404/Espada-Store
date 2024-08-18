package com.nqt.spring_boot_espada_store.controller;

import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.service.verifycode.VerifyCodeService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/verify")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VerifyCodeController {

    VerifyCodeService verifyCodeService;

    @NonFinal
    @Value("${success-request-code}")
    int SUCCESS_REQUEST_CODE;

    @GetMapping
    public ApiResponse<Object> verifyCode(@RequestParam("code") String code, @RequestParam("user") String userId, HttpServletResponse response)
            throws MessagingException, IOException {
        verifyCodeService.verify(code, userId);
        response.sendRedirect("http://localhost:3000/verification-success");
        return ApiResponse.builder()
                .code(SUCCESS_REQUEST_CODE)
                .message("Verify success")
                .build();
    }

}
