package com.teqmonic.keycloak.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Setter;

@Builder(toBuilder = true)
public record MenuCategoryResponse(@JsonProperty("category") String category, @JsonProperty("items") List<MenuItemResponse> menuItem) {

}
