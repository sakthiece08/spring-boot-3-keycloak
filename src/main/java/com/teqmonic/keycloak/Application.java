package com.teqmonic.keycloak;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.teqmonic.keycloak.bean.MenuItemCategory;
import com.teqmonic.keycloak.bean.MenuType;
import com.teqmonic.keycloak.entity.Menu;
import com.teqmonic.keycloak.entity.MenuItem;
import com.teqmonic.keycloak.entity.Restaurant;
import com.teqmonic.keycloak.repository.MenuItemRepository;
import com.teqmonic.keycloak.repository.MenuRepository;
import com.teqmonic.keycloak.repository.RestaurantRepository;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@SecurityScheme(name = "Keycloak",
                openIdConnectUrl = "http://localhost:8000/realms/Teqmonic/.well-known/openid-configuration",
                scheme = "bearer",
                type = SecuritySchemeType.OPENIDCONNECT,
                in = SecuritySchemeIn.HEADER
		)
@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = "com.teqmonic")
public class Application {

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private MenuItemRepository menuItemRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
 	@Transactional
	@Bean
	CommandLineRunner runner() {
		
		return arg -> {
			
			// Set up restaurant
			Restaurant restaurant = Restaurant.builder().name("Franklin").city("NewYork").cuisine("American").build();
			Menu menu =	Menu.builder().type(MenuType.BREAKFAST).restaurant(restaurant).build();			
			restaurant.setMenus(List.of(menu));		
		
			restaurantRepository.save(restaurant); 
			log.info("Restaurant {} has been set up", restaurant.getName());
			
			// Delete restaurant
		 	 //restaurantRepository.delete(restaurant);
			 //log.info("Restaurant {} has been deleted", restaurant.getName());
			// Deleting a restaurant will delete corresponding Menu as well
			
			// Add Menu item
			Optional<Menu> menu1 = menuRepository.findById(menu.getId());
			if(menu1.isPresent()) {
			    log.info("Menu {} ", menu1);
				MenuItem menuItem = MenuItem.builder().name("Coffee").price(2.50).category(MenuItemCategory.SNACK).build();
				menu1.get().setItems(Set.of(menuItem));	
				menuRepository.save(menu1.get());
				log.info("Menu item {} has been set up", menuItem);
				Optional<MenuItem> menuItem1  = menuItemRepository.findById(menuItem.getId());
				// Update menu item
				if(menuItem1.isPresent()) {
					menuItem1.get().setPrice(50.10);
					menuItemRepository.save(menuItem1.get());
					log.info("Menu item {} has been updated successfully", menuItem1);
				}
				log.info("Menu items count {}", menuItemRepository.count());
				// Delete Menu, should delete menu items and its mappings
				// menuRepository.delete(menu1.get());
				// log.info("Menu {} has been deleted successfully", menu1);
				// log.info("Menu items count {} after menu deletion", menuItemRepository.count());
			}
		
			

			
		};
		
		
	}
	

}
