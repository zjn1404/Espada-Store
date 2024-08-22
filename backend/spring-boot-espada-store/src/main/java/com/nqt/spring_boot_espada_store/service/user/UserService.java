package com.nqt.spring_boot_espada_store.service.user;

import java.io.UnsupportedEncodingException;
import java.util.List;

import jakarta.mail.MessagingException;

import com.nqt.spring_boot_espada_store.dto.request.user.UserCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.user.UserUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.UserResponse;

public interface UserService {

    UserResponse createUser(UserCreationRequest userRequest) throws MessagingException, UnsupportedEncodingException;

    UserResponse updateUser(String id, UserUpdateRequest userRequest);

    UserResponse updateUser(UserUpdateRequest userRequest);

    UserResponse getUserById(String id);

    UserResponse getMyInfo();

    List<UserResponse> getUsers();

    void deleteUser(String id);
}
