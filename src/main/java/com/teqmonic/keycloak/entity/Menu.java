package com.teqmonic.keycloak.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import com.teqmonic.keycloak.bean.MenuType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@Table(name = "SEC_MENU", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"id", "restaurant_id"})
	})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private MenuType type;

	@Column(name = "created_date")
	@CreationTimestamp
	private LocalDateTime createdDate;

	@ManyToOne
	private Restaurant restaurant;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "SEC_MENU_ITEM_MAPPING", 
	           joinColumns = { @JoinColumn(name = "menu_id") }, 
	           inverseJoinColumns = { @JoinColumn(name = "menu_item_id") })
	private Set<MenuItem> items;

}
