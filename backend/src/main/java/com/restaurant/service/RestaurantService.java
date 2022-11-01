package com.restaurant.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.domain.dto.request.CreateDishTo;
import com.restaurant.domain.dto.request.CreateRestaurantTo;
import com.restaurant.domain.dto.request.EditDishTo;
import com.restaurant.domain.dto.request.UpdateRestaurantTo;
import com.restaurant.domain.dto.response.CreateDishResponse;
import com.restaurant.domain.dto.response.RestaurantResponse;
import com.restaurant.domain.dto.response.GetRestaurantByName;
import com.restaurant.domain.mapper.RestaurantMapper;
import com.restaurant.domain.model.Dish;
import com.restaurant.domain.model.Restaurant;
import com.restaurant.domain.model.SafetyLicense;
import com.restaurant.domain.model.User;
import com.restaurant.exception.UserNotFoundException;
import com.restaurant.repository.IDishRepo;
import com.restaurant.repository.IRestaurantRepo;
import com.restaurant.repository.ISafetyLicenseRepo;
import com.restaurant.repository.IUserRepo;
import com.restaurant.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.restaurant.domain.mapper.DishMapper.toCreateDishFrom;
import static com.restaurant.domain.mapper.DishMapper.toDishFrom;
import static com.restaurant.domain.mapper.RestaurantMapper.*;
import static java.lang.String.format;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class RestaurantService {

    private final ISafetyLicenseRepo safetyLicenseRepo;
    private final IRestaurantRepo restaurantRepo;
    private final IDishRepo dishRepo;
    private final IUserRepo userRepo;

    public Set<RestaurantResponse> getRestaurantsByOwnerName(String username) {
        log.trace("RestaurantService - getRestaurantsByOwnerName");
        Set<Restaurant> restaurants = restaurantRepo.findByUserUsername(username);
        log.trace("Found restaurants {}", restaurants);
        return restaurants.stream().map(RestaurantMapper::toRestaurantResponse).collect(Collectors.toSet());
//        return RestaurantResponse;
    }

    public Set<GetRestaurantByName> getRestaurantsByName(String restaurantName) {
        log.trace("RestaurantService - getRestaurantsByName");
        return toGetRestaurantByNameFrom(restaurantRepo.findAllByName(restaurantName));
    }

    public Set<Dish> getAllRestaurantDishesByOwnerName(String username) {
        log.trace("RestaurantService - getAllRestaurantDishesByOwnerName");
        Set<Restaurant> restaurants = restaurantRepo.findByUserUsername(username);

        Set<Dish> dishes = new HashSet<>();
        restaurants.forEach(rest -> dishes.addAll(rest.getDishes()));
        log.trace("Restaurants found {}", restaurants);
        log.trace("Dishes found {}", dishes);
        return dishes;
    }

    public Restaurant getRestaurantById(UUID uuid) {
        return restaurantRepo.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException(format("Restaurant not found for id %s", uuid)));
    }

    // todo extract user from spring security context set by jwt token filter
    public Object registerRestaurant(String data, MultipartFile license) throws IOException {
        log.trace("Restaurant Service - registerRestaurant");

        ObjectMapper objectMapper = new ObjectMapper();
        CreateRestaurantTo dto = objectMapper.readValue(data, CreateRestaurantTo.class);

        log.trace("Fetching {}", dto.username());
        Optional<User> user = userRepo.findByUsername(dto.username());
        if (user.isEmpty()) {
            throw new UserNotFoundException("The restaurant owner's username is a required field");
        }

        Restaurant restaurant = toRestaurantFrom(dto, user.get());
        SafetyLicense safetyLicense = SafetyLicense.builder()
                .name(license.getOriginalFilename())
                .type(license.getContentType())
                .restaurant(restaurant)
                .license(FileUtil.compressData(license.getBytes()))
                .build();

        log.trace("Done creating restaurant and safety license objects");
        safetyLicenseRepo.save(safetyLicense);
        restaurantRepo.save(restaurant);

        log.trace("Done saving restaurant and safety license");
        return toRestaurantResponse(restaurant);
    }

    public RestaurantResponse createRestaurant(CreateRestaurantTo createRestaurantTo) {
        log.trace("Restaurant Service - createRestaurant {}", createRestaurantTo);

        log.trace("Fetching {}", createRestaurantTo.username());
        Optional<User> user = userRepo.findByUsername(createRestaurantTo.username());
        if (user.isPresent()) {

            log.trace("Found {}, creating restaurant", user.get().getUsername());
            Restaurant restaurant = toRestaurantFrom(createRestaurantTo, user.get());
            restaurant = restaurantRepo.save(restaurant);
            return toRestaurantResponse(restaurant);
        }
        throw new UserNotFoundException(format("Failed to create Restaurant. %s is not valid user", createRestaurantTo.username()));
    }

    public RestaurantResponse editRestaurant(UpdateRestaurantTo updateRestaurantTo) {
        log.trace("Restaurant Service - editRestaurant");
        Restaurant restaurant = restaurantRepo
                .findById(updateRestaurantTo.id())
                .orElseThrow(() -> new EntityNotFoundException(format("%s not found", updateRestaurantTo.restaurantName())));

        restaurantRepo.save(updateRestaurantProperties(updateRestaurantTo, restaurant));
        return toRestaurantResponse(restaurant);
    }

    public CreateDishResponse createDish(String dishJson, MultipartFile image) throws JsonProcessingException {
        log.trace("RestaurantService - createDish");

        ObjectMapper objectMapper = new ObjectMapper();
        CreateDishTo dto = objectMapper.readValue(dishJson, CreateDishTo.class);

        log.trace("Finding the restaurant by ist id {}", dto.restaurantId());
        UUID id = dto.restaurantId();
        Restaurant restaurant = restaurantRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        format("Restaurant not found for id %s", dto.restaurantId())));

        log.trace("Creating dish item {}", dto);
        Dish dish = toDishFrom(dto);
        dish.setRestaurant(restaurant);

        log.trace("Saving dish item");
        return toCreateDishFrom(dishRepo.save(dish));
    }

    public Object editRestaurantItem(UUID restaurantId, EditDishTo editDishTo) {
        log.trace("Restaurant Service - editRestaurantItem");
        Dish dish = dishRepo.findById(editDishTo.id())
                .orElseThrow(() -> new EntityNotFoundException(format("Cannot update - item id %s was not found", editDishTo.id())));

        log.trace("Updating fields {}", editDishTo);
        if (editDishTo.itemName() != null && !editDishTo.itemName().isBlank()) {
            dish.setName(editDishTo.itemName());
        }
        if (editDishTo.price() != null && !editDishTo.price().equals(dish.getPrice())) {
            dish.setPrice(editDishTo.price());
        }
        if (editDishTo.description() != null && !editDishTo.description().isBlank()) {
            dish.setDescription(editDishTo.description());
        }
        if (editDishTo.ingredients() != null && !editDishTo.ingredients().isBlank()) {
            dish.setIngredients(editDishTo.ingredients());
        }
        return dishRepo.save(dish);
    }

    public Set<Dish> getRestaurantItems(UUID restaurantId) {
        return dishRepo.findByRestaurantUuid(restaurantId);
    }

    public String removeRestaurant(UUID restaurantId) {
        log.trace("Removing restaurant of ID == {}", restaurantId);
        restaurantRepo.deleteById(restaurantId);
        return "SUCCESS";
    }

    public String removeRestaurantItem(Long itemId) {
        log.trace("Removing restaurant item {}", itemId);
        dishRepo.deleteById(itemId);
        return "SUCCESS";
    }

}
