package com.nqt.spring_boot_espada_store.controller;

import com.nqt.spring_boot_espada_store.dto.request.UserCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.UserUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.ApiResponse;
import com.nqt.spring_boot_espada_store.dto.response.UserResponse;
import com.nqt.spring_boot_espada_store.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        UserResponse userResponse = userService.createUser(request);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userResponse);

        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<UserResponse> updateUser(@PathVariable("id") String id, @RequestBody @Valid UserUpdateRequest request) {
        UserResponse userResponse = userService.updateUser(id, request);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userResponse);

        return apiResponse;
    }

    @PutMapping
    ApiResponse<UserResponse> updateUser(@RequestBody @Valid UserUpdateRequest request) {
        UserResponse userResponse = userService.updateUser(request);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userResponse);

        return apiResponse;
    }

    @GetMapping("/{id}")
    ApiResponse<UserResponse> getUser(@PathVariable("id") String id) {
        UserResponse userResponse = userService.getUserById(id);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userResponse);

        return apiResponse;
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        UserResponse userResponse = userService.getMyInfo();
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userResponse);

        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> responses = userService.getUsers();
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();

        apiResponse.setResult(responses);

        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<Object> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);

        ApiResponse<Object> apiResponse = new ApiResponse<>();

        apiResponse.setMessage("User has been deleted!");

        return apiResponse;
    }
}
