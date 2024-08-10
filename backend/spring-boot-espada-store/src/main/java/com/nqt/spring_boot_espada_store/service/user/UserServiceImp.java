package com.nqt.spring_boot_espada_store.service.user;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;

import com.nqt.spring_boot_espada_store.Utils;
import com.nqt.spring_boot_espada_store.entity.VerifyCode;
import com.nqt.spring_boot_espada_store.repository.VerifyCodeRepository;
import jakarta.mail.MessagingException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nqt.spring_boot_espada_store.dto.request.user.UserCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.user.UserUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.UserResponse;
import com.nqt.spring_boot_espada_store.entity.Role;
import com.nqt.spring_boot_espada_store.entity.User;
import com.nqt.spring_boot_espada_store.exception.AppException;
import com.nqt.spring_boot_espada_store.exception.ErrorCode;
import com.nqt.spring_boot_espada_store.mapper.UserMapper;
import com.nqt.spring_boot_espada_store.repository.RoleRepository;
import com.nqt.spring_boot_espada_store.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImp implements UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    VerifyCodeRepository verifyCodeRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    Utils utils;

    @Override
    public UserResponse createUser(UserCreationRequest request)
            throws MessagingException, UnsupportedEncodingException {
        if (userRepository.existsUserByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);

        List<Role> roles = roleRepository.findAllById(request.getRoles());

        user.setRoles(new HashSet<>(roles));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String role = request.getRoles().contains("ADMIN") ? "a" : "c";
        String id = String.format("%s%s%s", role, user.getUsername(), user.getPhoneNumber());
        user.setId(id);
        userRepository.save(user);

        if (request.getRoles().contains("USER")) {
            VerifyCode verifyCode = utils.generateVerifyCode(user);
            verifyCodeRepository.save(verifyCode);
            utils.sendVerificationEmail(verifyCode.getVerifyCode(), user);
        }

        return userMapper.toUserResponse(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return updateUser(request, user);
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return updateUser(request, user);
    }

    private UserResponse updateUser(UserUpdateRequest request, User user) {
        if (request.getRoles() != null) {
            List<Role> roles = roleRepository.findAllById(request.getRoles());
            user.setRoles(new HashSet<>(roles));
        }

        if (request.getPassword() != null) {
            request.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
