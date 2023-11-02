package com.teqmonic.keycloak.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.teqmonic.keycloak.entity.Restaurant;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {

	boolean existsByNameIgnoreCase(String name);

}
