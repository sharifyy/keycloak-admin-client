package com.example.keycloakapi;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;

@Configuration
public class KeycloakAdminConfig {

    @Bean
    public Keycloak admin(){
        return KeycloakBuilder.builder()
                .grantType(CLIENT_CREDENTIALS)
                .serverUrl("http://localhost:8080/auth")
                .realm("master")
                .clientId("admin-cli")
                .clientSecret("37f4d1fc-5aec-43c0-87f8-7fc2511e6646")
                .build();
    }
}
