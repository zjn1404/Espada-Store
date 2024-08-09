package com.nqt.spring_boot_espada_store.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.nqt.spring_boot_espada_store.dto.request.user.UserCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.user.UserUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.UserResponse;
import com.nqt.spring_boot_espada_store.service.user.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) throws MessagingException, UnsupportedEncodingException {
        UserResponse userResponse = userService.createUser(request);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userResponse);

        return apiResponse;
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable("id") String id, @RequestBody @Valid UserUpdateRequest request) {
        UserResponse userResponse = userService.updateUser(id, request);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userResponse);

        return apiResponse;
    }

    @PutMapping
    public ApiResponse<UserResponse> updateUser(@RequestBody @Valid UserUpdateRequest request) {
        UserResponse userResponse = userService.updateUser(request);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userResponse);

        return apiResponse;
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable("id") String id) {
        UserResponse userResponse = userService.getUserById(id);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userResponse);

        return apiResponse;
    }

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        UserResponse userResponse = userService.getMyInfo();
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userResponse);

        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> responses = userService.getUsers();
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();

        apiResponse.setResult(responses);

        return apiResponse;
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Object> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);

        ApiResponse<Object> apiResponse = new ApiResponse<>();

        apiResponse.setMessage("User has been deleted!");

        return apiResponse;
    }
}
