package com.teqmonic.keycloak.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonPropertyOrder({ "restaurant_name", "category_total", "menu" })
public class RestaurantResponseClassification {

	@JsonProperty("restaurant_name")
	private String name;

	@JsonProperty("category_total")
	private List<CategoryTotal> categoryTotalList;

	@JsonProperty("menu")
	private List<MenuResponseClassification> menu;
}
