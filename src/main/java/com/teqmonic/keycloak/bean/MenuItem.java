package com.teqmonic.keycloak.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MenuItem(

		@JsonProperty("name") String name, 
		@JsonProperty("price") double price,
		@JsonProperty("category") MenuItemCategory category

) {

}
