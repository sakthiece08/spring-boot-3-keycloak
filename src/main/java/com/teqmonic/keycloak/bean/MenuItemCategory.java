package com.teqmonic.keycloak.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MenuItemCategory {
	VEG("Veg"), MEAT("Meat"), SNACK("Snack");

	private final String value;
}
