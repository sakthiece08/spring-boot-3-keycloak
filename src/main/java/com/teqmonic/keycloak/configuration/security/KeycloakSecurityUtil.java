package com.teqmonic.keycloak.configuration.security;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeycloakSecurityUtil {

	private Keycloak keyCloak;

	@Value("${keycloak.realm}")
	private String realm;

	@Value("${keycloak.server}")
	private String server;

	@Value("${keycloak.client-id}")
	private String clientId;

	@Value("${keycloak.grant-type}")
	private String grantType;

	@Value("${keycloak.user-name}")
	private String userName;

	@Value("${keycloak.password}")
	private String password;

	public Keycloak getKeycloakInstance() {
		if(null == keyCloak)
			keyCloak = KeycloakBuilder.builder().clientId(clientId).realm(realm).serverUrl(server).grantType(grantType)
				.username(userName).password(password).build();
		
		return keyCloak;
	}

}
