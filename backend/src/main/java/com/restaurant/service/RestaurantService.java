package com.restaurant.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.domain.dto.request.TCreateDish;
import com.restaurant.domain.dto.request.TCreateRestaurant;
import com.restaurant.domain.dto.request.TEditDish;
import com.restaurant.domain.dto.request.TEditRestaurant;
import com.restaurant.domain.dto.response.VwDish;
import com.restaurant.domain.dto.response.RestaurantResponse;
import com.restaurant.domain.dto.response.VwRestaurant;
import com.restaurant.domain.mapper.RestaurantMapper;
import com.restaurant.domain.model.*;
import com.restaurant.exception.UserNotFoundException;
import com.restaurant.repository.*;
import com.restaurant.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
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

    private final DocumentService documentService;

    private final IPhotoRepo photoRepo;
    private final ISafetyLicenseRepo safetyLicenseRepo;
    private final IRestaurantRepo restaurantRepo;
    private final IDishRepo dishRepo;
    private final IUserRepo userRepo;

    public Set<RestaurantResponse> getRestaurantsByOwnerName(String username) {
        log.trace("RestaurantService - getRestaurantsByOwnerName");
        Set<Restaurant> restaurants = restaurantRepo.findByUserUsername(username);
        log.trace("Found restaurants {}", restaurants);
        return restaurants.stream().map(RestaurantMapper::toRestaurantViewFrom).collect(Collectors.toSet());
    }

    public Set<VwRestaurant> getRestaurantsByName(String restaurantName) {
        log.trace("RestaurantService - getRestaurantsByName");
        return toGetRestaurantByNameFrom(restaurantRepo.findAllByName(restaurantName));
    }

    public Set<Dish> getAllDishesByOwnerName(String username) {
        log.trace("RestaurantService - getAllDishesByOwnerName");

        log.trace("Finding all restaurants owned by {}", username);
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

    public Object registerRestaurant(TCreateRestaurant tCreateRestaurant, MultipartFile license) throws IOException {
        log.trace("Restaurant Service - registerRestaurant");

        log.trace("Fetching {}", tCreateRestaurant.user().username());
        Optional<User> user = userRepo.findByUsername(tCreateRestaurant.user().username());
        if (user.isEmpty()) {
            throw new UserNotFoundException("The restaurant owner's username is a required field");
        }

        Restaurant restaurant = toRestaurantFrom(tCreateRestaurant, user.get());
        License safetyLicense = License.builder()
                .name(license.getOriginalFilename())
                .type(license.getContentType())
                .restaurant(restaurant)
                .file(FileUtil.compressData(license.getBytes()))
                .build();

        log.trace("Done creating restaurant and safety license objects");
        safetyLicenseRepo.save(safetyLicense);
        restaurantRepo.save(restaurant);

        log.trace("Done saving restaurant and safety license");
        return toRestaurantViewFrom(restaurant);
    }

    public RestaurantResponse createRestaurant(TCreateRestaurant tCreateRestaurant) {
        log.trace("Restaurant Service - TCreateRestaurant {}", tCreateRestaurant);

        log.trace("Fetching {}", tCreateRestaurant.user().username());
        Optional<User> user = userRepo.findByUsername(tCreateRestaurant.user().username());
        if (user.isPresent()) {

            log.trace("Found {}, creating restaurant", user.get().getUsername());
            Restaurant restaurant = toRestaurantFrom(tCreateRestaurant, user.get());
            restaurant = restaurantRepo.save(restaurant);
            return toRestaurantViewFrom(restaurant);
        }
        throw new UserNotFoundException(format("Failed to create Restaurant. %s is not valid user", tCreateRestaurant.user().username()));
    }

    public RestaurantResponse editRestaurant(String restaurantJson, MultipartFile restaurantPhoto) throws JsonProcessingException {
        log.trace("Restaurant Service - TEditRestaurant");

        ObjectMapper objMapper = new ObjectMapper();
        TEditRestaurant tEditRestaurant = objMapper.readValue(restaurantJson, TEditRestaurant.class);

        log.trace("Getting the restaurant to update");
        Restaurant restaurant = restaurantRepo
                .findById(tEditRestaurant.id())
                .orElseThrow(() -> new EntityNotFoundException(format("%s not found", tEditRestaurant.restaurantName())));

        log.trace("Preparing to update");
        restaurantRepo.save(updateRestaurantProperties(tEditRestaurant, restaurant));

        log.trace("Done updating the restaurant");
        return toRestaurantViewFrom(restaurant);
    }

    public VwDish createDish(String dishJson, MultipartFile photoFile) {
        log.trace("RestaurantService - createDish");

        ObjectMapper objectMapper = new ObjectMapper();
        TCreateDish dto = null;
        try {
            dto = objectMapper.readValue(dishJson, TCreateDish.class);
            log.trace("dto {}", dto);
        } catch (JsonProcessingException ex) {
            log.trace(ex.getLocalizedMessage());
            throw new ValidationException("Invalid format " + ex.getLocalizedMessage());
        }

        log.trace("Finding the restaurant by phone {}", dto.phone());
        Restaurant restaurant = restaurantRepo.findByPhone(dto.phone())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found for phone"));

        log.trace("Creating dish item {}", dto);
        Dish dish = toDishFrom(dto);
        dish.setRestaurant(restaurant);

        // process photo
        Photo photo = null;
        try {
            photo = documentService.processPhoto(photoFile);
            log.trace("Adding photo {} ", dish);
//            dish.addPhoto(photo);
            log.trace("Done saving dish");
            photo.setDish(dish);

            log.trace("start saving photo");
            photoRepo.save(photo);
            dish = dishRepo.save(dish);
        } catch(IOException ex) {
            log.error(format("Unable to process %s", photoFile.getName()));
        }

//        if (photo != null) {
//            photo.setDish(dish);
//            photoRepo.save(photo);
//        }

        log.trace("Saving dish item");
//        return "Dished saved";
        return toCreateDishFrom(dish);
    }

    public Object editDish(UUID restaurantId, TEditDish tEditDish) {
        log.trace("Restaurant Service - TEditDish");
        Dish dish = dishRepo.findById(tEditDish.id())
                .orElseThrow(() -> new EntityNotFoundException(format("Cannot update - dish id %s was not found", tEditDish.id())));

        log.trace("Updating fields {}", tEditDish);
        if (tEditDish.itemName() != null && !tEditDish.itemName().isBlank()) {
            dish.setName(tEditDish.itemName());
        }
        if (tEditDish.price() != null && !tEditDish.price().equals(dish.getPrice())) {
            dish.setPrice(tEditDish.price());
        }
        if (tEditDish.description() != null && !tEditDish.description().isBlank()) {
            dish.setDescription(tEditDish.description());
        }
        if (tEditDish.ingredients() != null && !tEditDish.ingredients().isBlank()) {
            dish.setIngredients(tEditDish.ingredients());
        }
        return dishRepo.save(dish);
    }

    public Set<Dish> getRestaurantItems(UUID restaurantId) {
        return dishRepo.findByRestaurantUuid(restaurantId);
    }

    public String removeRestaurant(UUID restaurantId) {
        log.trace("Removing restaurant of ID == {}", restaurantId);

        log.trace("Fetching restaurant {} to delete", restaurantId);
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(format("Cannot delete, unable to locate restaurant id %s", restaurantId)));
        restaurantRepo.delete(restaurant);
        return "SUCCESS";
    }

    public String removeDish(Long dishId) {
        log.trace("Restaurant Service - removeDish");

        log.trace("Fetching dish {} to delete", dishId);
        Dish dish = dishRepo.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException(format("Cannot delete, unable to locate dish id %s", dishId)));
        dishRepo.delete(dish);
        return "SUCCESS";
    }

}
