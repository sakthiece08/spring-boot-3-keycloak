package com.teqmonic.keycloak.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MenuType {
	BREAKFAST("Breakfast"), LUNCH("Lunch"), DINNER("Dinner");

	private final String value;
}
