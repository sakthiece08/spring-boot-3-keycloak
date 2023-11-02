package com.teqmonic.keycloak.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.teqmonic.keycloak.entity.MenuItem;

@Repository
public interface MenuItemRepository extends CrudRepository<MenuItem, Integer> {

}

