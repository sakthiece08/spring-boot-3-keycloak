package com.teqmonic.keycloak.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "Restaurant name is required")
	@Size(max = 30, message = "Restaurant name must be atmost 30 characters")
	@Column(name = "name")
	private String name;
	
	@NotBlank(message = "City is required")
	@Size(max = 20, message = "City must be atmost 30 characters")
	@Column(name = "city")
	private String city;
	
	@NotBlank(message = "Cuisine is required")
	@Size(max = 20, message = "Cuisine must be atmost 20 characters")
	@Column(name = "cuisine")
	private String cuisine;
	
	@JsonIgnore
	@Column(name = "created_on", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	
	// All the operations should be cascaded from Parent entity to Child entity
	@JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = false)
    @Singular("menu")
	private List<Menu> menus;

}
