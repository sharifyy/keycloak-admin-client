package com.example.keycloakapi;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;
import org.keycloak.representations.idm.authorization.ResourcePermissionRepresentation;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;

@SpringBootApplication
public class KeyCloakApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeyCloakApiApplication.class, args);
	}


		@Bean
	ApplicationRunner applicationRunner(){
		return args -> {
			Keycloak keycloak = KeycloakBuilder.builder()
					.grantType(CLIENT_CREDENTIALS)
					.serverUrl("http://localhost:8080/auth")
					.realm("master")
					.clientId("admin-cli")
					.clientSecret("37f4d1fc-5aec-43c0-87f8-7fc2511e6646")
					.build();

			PolicyRepresentation resourcePermissionRepresentation = keycloak.realm("spring-demo")
					.clients().get("d74441af-67c2-43a8-98cc-a0a3660f0430")
					.authorization()
					.policies().policy("4fb6962a-c3d4-45af-ad2e-ab2b5e08d6f6").toRepresentation();
//					.permissions().resource().findById("4fb6962a-c3d4-45af-ad2e-ab2b5e08d6f6").toRepresentation();
			System.out.println(resourcePermissionRepresentation);
//					.policies()
//					.policies()
//					.forEach(policyRepresentation -> System.out.println(policyRepresentation.getName()+", "+policyRepresentation.getId()));

		};
	}

//	private void print(ResourceRepresentation userRepresentation) {
//		System.out.println(userRepresentation.getName());
//	}
}
