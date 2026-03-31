package com.studentmanagement.lecturer.config;

import java.util.List;

import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiServersConfig {

    private static final String SERVER_URL = "http://localhost:8082";

    @Bean
    public OpenApiCustomizer swaggerServers() {
        return openApi -> openApi.setServers(List.of(
                new Server().url(SERVER_URL).description("Lecturer Service")));
    }
}
