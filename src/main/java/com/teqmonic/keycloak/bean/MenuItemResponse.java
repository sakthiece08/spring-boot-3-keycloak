package com.teqmonic.keycloak.bean;

import lombok.Builder;

@Builder
public record MenuItemResponse( String name, double price) {

}
