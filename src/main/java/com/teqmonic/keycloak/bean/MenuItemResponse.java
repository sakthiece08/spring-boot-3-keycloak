package com.teqmonic.keycloak.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record MenuItemResponse(@JsonProperty("item_id") int itemId, String name, double price) {

}
