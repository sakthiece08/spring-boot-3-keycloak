package com.teqmonic.keycloak.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.teqmonic.keycloak.bean.MenuType;
import com.teqmonic.keycloak.entity.Menu;

@Repository
public interface MenuRepository extends CrudRepository<Menu, Integer> {

	boolean existsBytypeAndRestaurantId(MenuType type, int restaturantId);
}
