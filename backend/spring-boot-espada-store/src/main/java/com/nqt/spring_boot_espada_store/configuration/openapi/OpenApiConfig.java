package com.nqt.spring_boot_espada_store.configuration.openapi;

import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(
            @Value("${open.api.title}") String title,
            @Value("${open.api.description}") String description,
            @Value("${open.api.version}") String version,
            @Value("${open.api.server.url}") String serverUrl,
            @Value("${open.api.server.description}") String serverDescription,
            @Value("${initial-admin-account.username}") String username,
            @Value("${initial-admin-account.password}") String password) {

        description += String.format(
                "<br>An admin account has been created with <b>username: %s</b> and <b>password: %s</b>. You can use it to access all APIs.",
                username, password);

        return new OpenAPI()
                .info(new Info().title(title).version(version).description(description))
                .servers(List.of(new Server().url(serverUrl).description(serverDescription)))
                .components(new Components()
                        .addSecuritySchemes(
                                "bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .security(List.of(new SecurityRequirement().addList("bearerAuth")));
    }

    @Bean
    public GroupedOpenApi groupedOpenApi(@Value("${open.api.group.package-to-scan}") String packageToScan) {
        return GroupedOpenApi.builder()
                .group("public")
                .packagesToScan(packageToScan)
                .build();
    }
}
