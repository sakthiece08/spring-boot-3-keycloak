package com.teqmonic.keycloak.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teqmonic.keycloak.bean.CategoryTotal;
import com.teqmonic.keycloak.bean.MenuCategoryResponse;
import com.teqmonic.keycloak.bean.MenuItemResponse;
import com.teqmonic.keycloak.bean.MenuRequest;
import com.teqmonic.keycloak.bean.MenuResponse;
import com.teqmonic.keycloak.bean.MenuResponse.MenuResponseBuilder;
import com.teqmonic.keycloak.bean.MenuResponseClassification;
import com.teqmonic.keycloak.bean.RestaurantRequest;
import com.teqmonic.keycloak.bean.RestaurantResponse;
import com.teqmonic.keycloak.bean.RestaurantResponseClassification;
import com.teqmonic.keycloak.entity.Menu;
import com.teqmonic.keycloak.entity.MenuItem;
import com.teqmonic.keycloak.entity.Restaurant;
import com.teqmonic.keycloak.exception.RestaurantNotFoundException;
import com.teqmonic.keycloak.repository.MenuItemRepository;
import com.teqmonic.keycloak.repository.MenuRepository;
import com.teqmonic.keycloak.repository.RestaurantRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/")
@SecurityRequirement(name = "Keycloak") // @SecurityScheme name
public class RestaurantController {

	private final RestaurantRepository restaurantRepository;
	
	private final MenuRepository menuRepository;
	
	private final MenuItemRepository menuItemRepository;

	@PreAuthorize("hasRole('admin')")
	@PostMapping("restaurant")
	public ResponseEntity<HttpStatus> createRestaurant(@RequestBody RestaurantRequest request) {
		log.info("Create restaurant {}", request);
		// check request replay
		if (restaurantRepository.existsByNameIgnoreCase(request.name())) {
			throw new RuntimeException("Restaurant - " + request.name() + " is already present");
		}

		restaurantRepository.save(Restaurant.builder().name(request.name()).city(request.city()).cuisine(request.cuisine()).build());
		log.info("Create restaurant for {} has been completed", request);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	// public API
	@GetMapping("public/restaurant")
	public Iterable<Restaurant> getRestaurants() {
		return restaurantRepository.findAll();
	}
	
	@PreAuthorize("hasRole('manager')")
	@PostMapping("menu")
	public ResponseEntity<HttpStatus> addMenu(@RequestBody MenuRequest request) {
		log.info("Add menu {}", request);
		
		// check request replay
		if(menuRepository.existsBytypeAndRestaurantId(request.type(), request.restaurantId())) {
			throw new RuntimeException("Menu - " + request.type() + " is already exist for the Restaurant Id - " + request.restaurantId());
		}
		// check for presence of requested Restaurant
		Restaurant restaurantEntity = restaurantRepository.findById(request.restaurantId()).orElseThrow(
				() -> new RestaurantNotFoundException("Restaurant id " + request.restaurantId() + " is not present"));
		
		Menu menu =	Menu.builder().type(request.type()).restaurant(restaurantEntity).build();
		// Convert MenuItems 
		Set<MenuItem> menuItem = Optional.ofNullable(request.items())
		.map(Set::stream).orElseGet(Stream::empty)
		.map(item -> MenuItem.builder().name(item.name()).price(item.price()).category(item.category()).build())
		.collect(Collectors.toSet());
		
		menu.setItems(menuItem);
		menuRepository.save(menu);
		
		log.info("Add menu {} has been completed", request);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('owner')")
	@PatchMapping("updatePrice/{menu_item_id}/latest-price/{price}")
	public ResponseEntity<HttpStatus> updatePrice(@PathVariable(name = "menu_item_id", required = true) int menuItemId,
			@PathVariable(name = "price", required = true)  double price) {
		
		log.info("Update menu item {}, price {}", menuItemId, price);
		MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> new RuntimeException("Menu item id " + menuItemId + " is not present"));
		menuItem.setPrice(price);
		menuItemRepository.save(menuItem);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// public API
	@GetMapping("public/menus")
	public ResponseEntity<List<RestaurantResponse>> getAllMenus() {
		
		List<Restaurant> restaurant = (List<Restaurant>) restaurantRepository.findAll();
		List<RestaurantResponse> list = new ArrayList<>();
		
		for(Restaurant entity: restaurant) {
			List<MenuResponse> menuList = new ArrayList<>();
			
			for (Menu menu : entity.getMenus()) {
				List<MenuItemResponse> menuItemList = new ArrayList<>();
				MenuResponse menuResponse = MenuResponse.builder().name(menu.getType().getValue()).build();
				for (MenuItem item : menu.getItems()) {
					menuItemList.add(MenuItemResponse.builder().name(item.getName()).price(item.getPrice()).itemId(item.getId()).build());
				}
				MenuResponseBuilder menuBuilder = menuResponse.toBuilder();
				menuList.add(menuBuilder.menuItem(menuItemList).build());
			}	
			list.add(RestaurantResponse.builder().name(entity.getName()).menu(menuList).build());
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// public API
	@GetMapping("public/menu-classification/{restaurant_id}")
	public ResponseEntity<RestaurantResponseClassification> getMenuClassification(@PathVariable(name = "restaurant_id", required = true) int restaurantId) {
		
		Optional<Restaurant> restaurant =restaurantRepository.findById(restaurantId);
		if(restaurant.isEmpty())
			throw new RestaurantNotFoundException("Restaurant id " + restaurantId + " is not present");
		
		List<MenuResponseClassification> MenuResponseClassificationList = new ArrayList<>();
		Map<String, List<Menu>>  menuTypeMap = restaurant.get().getMenus().stream().collect(Collectors.groupingBy(menu -> menu.getType().getValue()));
		Iterator<Map.Entry<String, List<Menu>>> menuTypeIterator = menuTypeMap.entrySet().iterator(); //E.g: Map<"dinner", List<Menu>>
		Map<String, CategoryTotal> categoryTotalMap = new HashMap<>();
		List<CategoryTotal> categoryTotalList = new ArrayList<>();
		
		while(menuTypeIterator.hasNext()) {
			Map.Entry<String, List<Menu>> menuTypeEntry = menuTypeIterator.next();
			Menu menu = menuTypeEntry.getValue().get(0); // there is always unique MenuType entry
			List<MenuCategoryResponse> menuCategoryResponseList = new ArrayList<>();
			// E.g: Map<"veg", Set<MenuItem>>
			Map<String, Set<MenuItem>> set = menu.getItems().stream()
					.collect(Collectors.groupingBy(item -> item.getCategory().getValue(), Collectors.toSet()));
			Iterator<Map.Entry<String, Set<MenuItem>>> categoryIterator = set.entrySet().iterator();
			while(categoryIterator.hasNext()) {
				List<MenuItemResponse> menuItemResponseList = new ArrayList<>();
				Map.Entry<String, Set<MenuItem>> itemCategory = categoryIterator.next();	
				
				String category = itemCategory.getKey(); // get Category
				CategoryTotal categoryTotal = categoryTotalMap.getOrDefault(category, new CategoryTotal(category, 0));
				categoryTotal.setTotalItems(categoryTotal.getTotalItems() + itemCategory.getValue().size());
				categoryTotalMap.put(category, categoryTotal);
					
				Set<MenuItem> itemSet = itemCategory.getValue();
				Iterator<MenuItem> it = itemSet.iterator();
				while(it.hasNext()) {
					MenuItem item = it.next();
					menuItemResponseList.add(MenuItemResponse.builder().name(item.getName()).price(item.getPrice()).itemId(item.getId()).build());
				}
				// sort items by price
				Collections.sort(menuItemResponseList, (a, b) -> Double.compare(a.price(), b.price()));
				// Collections.sort(menuItemResponseList, Comparator.comparingDouble(MenuItemResponse::price));
				menuCategoryResponseList.add(MenuCategoryResponse.builder().category(category).menuItem(menuItemResponseList).build());					
			}
			
			MenuResponseClassificationList.add(MenuResponseClassification.builder().type(menuTypeEntry.getKey()).details(menuCategoryResponseList).build());
		}
		// List of CategoryTotal
		Iterator<Map.Entry<String, CategoryTotal>> categoryIterator =  categoryTotalMap.entrySet().iterator();
		while(categoryIterator.hasNext()) {
			Map.Entry<String, CategoryTotal> menuTypeEntry = categoryIterator.next();
			categoryTotalList.add(menuTypeEntry.getValue());
		}
		
		RestaurantResponseClassification response =	RestaurantResponseClassification.builder().name(restaurant.get().getName())
				.categoryTotalList(categoryTotalList).menu(MenuResponseClassificationList).build();
		
		return new ResponseEntity<>(response, HttpStatus.OK);	
		
	}
}
