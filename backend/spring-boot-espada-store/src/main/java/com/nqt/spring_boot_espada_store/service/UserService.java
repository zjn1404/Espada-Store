package com.nqt.spring_boot_espada_store.service;

import com.nqt.spring_boot_espada_store.dto.request.UserCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.UserUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserCreationRequest userRequest);

    UserResponse updateUser(String id, UserUpdateRequest userRequest);

    UserResponse updateUser(UserUpdateRequest userRequest);

    UserResponse getUserById(String id);

    UserResponse getMyInfo();

    List<UserResponse> getUsers();

    void deleteUser(String id);
}
