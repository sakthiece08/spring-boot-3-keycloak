package com.teqmonic.keycloak.bean;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MenuRequest(

		@JsonProperty("menu_type") 
		MenuType type,
		
		@JsonProperty("restaurant_id")
		Integer restaurantId,
		
		@JsonProperty("menu_items")
		Set<MenuItem> items

) {

}
