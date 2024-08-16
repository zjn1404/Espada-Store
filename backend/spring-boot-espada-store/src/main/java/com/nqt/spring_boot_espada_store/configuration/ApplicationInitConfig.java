package com.nqt.spring_boot_espada_store.configuration;

import com.nqt.spring_boot_espada_store.entity.Role;
import com.nqt.spring_boot_espada_store.entity.User;
import com.nqt.spring_boot_espada_store.repository.RoleRepository;
import com.nqt.spring_boot_espada_store.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

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
            havingValue = "com.mysql.cj.jdbc.Driver"
    )
    public ApplicationRunner applicationRunner() {
        return args -> {
            if (!roleRepository.existsById("ADMIN")) {
                Role adminRole = Role.builder()
                        .name("ADMIN")
                        .build();
                roleRepository.save(adminRole);

                User admin = User.builder()
                        .id("admin")
                        .username("admin")
                        .password("adminInit123")
                        .email("admin@admin.com")
                        .phoneNumber("0123456789")
                        .roles(Set.of(adminRole))
                        .firstName("Admin")
                        .lastName("Admin")
                        .build();

                userRepository.save(admin);
            }
        };
    }
}
