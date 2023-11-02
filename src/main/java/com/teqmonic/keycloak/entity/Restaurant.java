package com.teqmonic.keycloak.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Builder
@Entity
@Table(name = "SEC_RESTAURANT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "created_on", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	
	// All the operations should be cascaded from Parent entity to Child entity
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = false)
    @Singular("menu")
	private List<Menu> menus;

}
