package com.teqmonic.keycloak.entity;

import org.hibernate.validator.constraints.Range;

import com.teqmonic.keycloak.bean.MenuItemCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

	@NotBlank(message = "MenuItem name is required")
	@Size(max = 20, message = "MenuItem name must be atmost 30 characters")
	@Column(name = "name")
	private String name;

	@Range(min=0, max=1000)
	@Column(name = "price")
	private double price;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "category")
	private MenuItemCategory category;

}
