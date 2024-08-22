package com.nqt.spring_boot_espada_store.configuration.security;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nqt.spring_boot_espada_store.entity.Role;
import com.nqt.spring_boot_espada_store.entity.User;
import com.nqt.spring_boot_espada_store.repository.RoleRepository;
import com.nqt.spring_boot_espada_store.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    RoleRepository roleRepository;
    UserRepository userRepository;

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    public ApplicationRunner applicationRunner(
            @Value("${initial-admin-account.id}") String id,
            @Value("${initial-admin-account.username}") String username,
            @Value("${initial-admin-account.password}") String password,
            @Value("${initial-admin-account.email}") String email,
            @Value("${initial-admin-account.phoneNumber}") String phoneNumber,
            @Value("${initial-admin-account.role}") String role,
            @Value("${initial-admin-account.firstName}") String firstName,
            @Value("${initial-admin-account.lastName}") String lastName) {
        return args -> {
            if (!roleRepository.existsById("ADMIN")) {
                Role adminRole = Role.builder().name(role).build();
                roleRepository.save(adminRole);

                User admin = User.builder()
                        .id(id)
                        .username(username)
                        .password(password)
                        .email(email)
                        .phoneNumber(phoneNumber)
                        .roles(Set.of(adminRole))
                        .firstName(firstName)
                        .lastName(lastName)
                        .build();

                userRepository.save(admin);
            }
        };
    }
}
