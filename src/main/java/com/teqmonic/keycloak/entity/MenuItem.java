package com.teqmonic.keycloak.entity;

import com.teqmonic.keycloak.bean.MenuItemCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "SEC_MENU_ITEM")
@Entity
public class MenuItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_item_id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private double price;

	@Enumerated(EnumType.STRING)
	@Column(name = "category")
	private MenuItemCategory category;

}
