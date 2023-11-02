package com.teqmonic.keycloak.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Setter;

@Builder(toBuilder = true)
public record MenuResponseClassification(@JsonProperty("type") String type, @JsonProperty("details") List<MenuCategoryResponse> details) {

}
