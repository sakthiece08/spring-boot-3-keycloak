package com.teqmonic.keycloak.bean.security;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record User(@JsonProperty("first_name") String firstName, @JsonProperty("last_name") String LastName,
		@JsonProperty("user_name") String UserName, @JsonProperty("email") String Email, @JsonProperty("password") String password,
		@JsonProperty("realm_roles") List<String> realmRoles) {

}
