package com.teqmonic.keycloakclient;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teqmonic.keycloak.bean.security.User;
import com.teqmonic.keycloak.configuration.security.KeycloakSecurityUtil;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("keycloak/")
@SecurityRequirement(name = "Keycloak")
public class UserResource {
	
	@Autowired
	private KeycloakSecurityUtil keycloakSecurityUtil;

	@Value("${keycloak.realm}")
	private String realm;
	
	@PreAuthorize("hasRole('admin')")
	@GetMapping("users")
	public List<User> getUsers(){
		Keycloak KeyCloak = keycloakSecurityUtil.getKeycloakInstance();
		List<UserRepresentation> userRepresentation = KeyCloak.realm(realm).users().list();
		
		return userRepresentation.stream().map(userRep -> new User(userRep.getFirstName(), userRep.getLastName(),
				userRep.getUsername(), userRep.getEmail(), "", userRep.getRealmRoles())).toList();
	}
	
	@PreAuthorize("hasRole('admin')")
	@PostMapping
	@RequestMapping("/user")
	public ResponseEntity<HttpStatus>  createUser(@RequestBody User user) {
		log.info("Create Keycloak user {}", user);
		UserRepresentation userRep = mapUserRep(user);
		log.info("Create Keycloak, UserRepresentation {}", userRep);
		Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
		keycloak.realm(realm).users().create(userRep);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	private UserRepresentation mapUserRep(User user) {
		UserRepresentation userRep = new UserRepresentation();
		userRep.setUsername(user.UserName());
		userRep.setFirstName(user.firstName());
		userRep.setLastName(user.LastName());
		userRep.setEmail(user.Email());
		userRep.setEnabled(true);
		userRep.setEmailVerified(true);
		List<CredentialRepresentation> creds = new ArrayList<>();
		CredentialRepresentation cred = new CredentialRepresentation();
		cred.setTemporary(false);
		cred.setValue(user.password());
		creds.add(cred);
		userRep.setCredentials(creds);
		return userRep;
	}
	
}
