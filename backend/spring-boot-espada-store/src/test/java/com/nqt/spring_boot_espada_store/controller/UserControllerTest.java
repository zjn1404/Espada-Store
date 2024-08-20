package com.nqt.spring_boot_espada_store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nqt.spring_boot_espada_store.dto.request.user.UserCreationRequest;
import com.nqt.spring_boot_espada_store.dto.request.user.UserUpdateRequest;
import com.nqt.spring_boot_espada_store.dto.response.UserResponse;
import com.nqt.spring_boot_espada_store.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserCreationRequest userCreationRequest;
    private UserCreationRequest userCreationRequestWithUsernameInvalid;
    private UserCreationRequest userCreationRequestWithPasswordInvalid;
    private UserUpdateRequest userUpdateRequest;
    private UserResponse userResponse;
    private Set<String> roles = new HashSet<>();

    @BeforeEach
    void initData() {
        roles.add("USER");

        userCreationRequest = UserCreationRequest.builder()
                .username("tuong14")
                .password("Tuong123")
                .email("tuong14@gmail.com")
                .firstName("Nguyen")
                .lastName("Tuong")
                .phoneNumber("0912345678")
                .roles(roles)
                .build();

        userCreationRequestWithUsernameInvalid = UserCreationRequest.builder()
                .username("tu")
                .password("Tuong123")
                .email("tuong14@gmail.com")
                .firstName("Nguyen")
                .lastName("Tuong")
                .phoneNumber("0912345678")
                .roles(roles)
                .build();

        userCreationRequestWithPasswordInvalid = UserCreationRequest.builder()
                .username("tuong14")
                .password("123")
                .email("tuong14@gmail.com")
                .firstName("Nguyen")
                .lastName("Tuong")
                .phoneNumber("0912345678")
                .roles(roles)
                .build();

        userUpdateRequest = UserUpdateRequest.builder()
                .password("Tuong123")
                .email("tuong14@gmail.com")
                .firstName("Nguyen")
                .lastName("Tuong")
                .phoneNumber("0912345678")
                .roles(roles)
                .build();

        userResponse = UserResponse.builder()
                .id("tuong140912345678")
                .username("tuong14")
                .password("Tuong123")
                .email("tuong14@gmail.com")
                .firstName("Nguyen")
                .lastName("Tuong")
                .phoneNumber("0912345678")
                .build();
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userCreationRequest);
        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userResponse);
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("1000"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("tuong140912345678"));
    }

    @Test
    void createUser_usernameInvalid_fail() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userCreationRequestWithUsernameInvalid);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("2001"));
    }

    @Test
    void createUser_passwordInvalid_fail() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userCreationRequestWithPasswordInvalid);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("2002"));
    }

    @Test
    @WithMockUser(username = "tuong14")
    void updateUser_validRequest_success() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userUpdateRequest);
        Mockito.when(userService.updateUser(ArgumentMatchers.any())).thenReturn(userResponse);
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                .put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("1000"));
    }

    @Test
    @WithMockUser(username = "tuong14")
    void updateUserWithId_validRequest_success() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userUpdateRequest);
        Mockito.when(userService.updateUser(ArgumentMatchers.eq(userResponse.getId()),ArgumentMatchers.any())).thenReturn(userResponse);
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/user/" + userResponse.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("1000"));
    }

    @Test
    @WithMockUser(username = "tuong14")
    void getUserById_validRequest_success() throws Exception {
        // GIVEN
        Mockito.when(userService.getUserById(ArgumentMatchers.any())).thenReturn(userResponse);
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/"+userResponse.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("1000"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("tuong140912345678"));
    }

    @Test
    @WithMockUser(username = "tuong14")
    void getMyInfo_validRequest_success() throws Exception {
        // GIVEN
        Mockito.when(userService.getMyInfo()).thenReturn(userResponse);
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/my-info"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("1000"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("tuong140912345678"));
    }

    @Test
    @WithMockUser(username = "tuong14")
    void getAllUser_validRequest_success() throws Exception {
        // GIVEN
        Mockito.when(userService.getUsers()).thenReturn(List.of(userResponse));
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("1000"));
    }

    @Test
    @WithMockUser(username = "tuong14")
    void deleteUser_validRequest_success() throws Exception {
        // GIVEN
        Mockito.doNothing().when(userService).deleteUser(ArgumentMatchers.any());
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/user/" + userResponse.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("1000"));
    }
}
