package com.nqt.spring_boot_espada_store.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nqt.spring_boot_espada_store.dto.request.security.*;
import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.AuthenticationResponse;
import com.nqt.spring_boot_espada_store.dto.response.IntrospectResponse;
import com.nqt.spring_boot_espada_store.service.authentication.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Authentication Controller")
public class AuthenticationController {

    AuthenticationService authenticationService;

    @NonFinal
    @Value("${success-request-code}")
    int SUCCESS_REQUEST_CODE;

    @PostMapping("/token")
    @Operation(summary = "Login", description = "API used to login and create access token, refresh token.")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);

        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(response);

        return apiResponse;
    }

    @PostMapping("/introspect")
    @Operation(summary = "Introspect token", description = "API introspects token.")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) {
        IntrospectResponse response = authenticationService.introspect(request);

        ApiResponse<IntrospectResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(response);

        return apiResponse;
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh token", description = "API refreshes token.")
    public ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshTokenRequest request) {
        AuthenticationResponse response = authenticationService.refreshToken(request);

        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(response);

        return apiResponse;
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "API used to log out and invalidate access token, refresh token")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request) {
        authenticationService.logout(request);

        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Successfully logged out");

        return apiResponse;
    }

    @PostMapping("/change-password")
    @Operation(summary = "Change password", description = "API changes password.")
    public ApiResponse<Object> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        authenticationService.changePassword(request);

        return ApiResponse.builder()
                .code(SUCCESS_REQUEST_CODE)
                .message("Successfully changed password")
                .build();
    }
}
