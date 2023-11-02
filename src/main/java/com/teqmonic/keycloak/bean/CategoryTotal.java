package com.teqmonic.keycloak.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryTotal {

	@JsonProperty("category")
	private String category;
	
	@JsonProperty("total_items")
	private int totalItems;
}
