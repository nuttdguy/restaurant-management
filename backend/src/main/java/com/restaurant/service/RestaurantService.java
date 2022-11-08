package com.restaurant.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.domain.dto.request.TCreateDish;
import com.restaurant.domain.dto.request.TCreateRestaurant;
import com.restaurant.domain.dto.request.TEditDish;
import com.restaurant.domain.dto.request.TEditRestaurant;
import com.restaurant.domain.dto.response.VwDish;
import com.restaurant.domain.dto.response.VwRestaurant;
import com.restaurant.domain.model.*;
import com.restaurant.exception.NotFoundException;
import com.restaurant.exception.UserNotFoundException;
import com.restaurant.repository.*;
import com.restaurant.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.io.IOException;
import java.util.*;

import static com.restaurant.domain.mapper.DishMapper.*;
import static com.restaurant.domain.mapper.RestaurantMapper.*;
import static com.restaurant.exception.ExceptionMessage.NOT_FOUND;
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

    public Set<VwRestaurant> getRestaurantsByOwnerName(String username) {
        log.trace("RestaurantService - getRestaurantsByOwnerName");
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(format(NOT_FOUND, username)));
        Set<Restaurant> restaurants = restaurantRepo.findFirst5ByUserUuid(user.getUuid());

        return toRestaurantSetViewFrom(restaurants);
    }

    public Set<VwRestaurant> getRestaurantsByName(String restaurantName) {
        log.trace("RestaurantService - getRestaurantsByName");
        return toRestaurantSetViewFrom(restaurantRepo.findAllByName(restaurantName));
    }

    public Set<VwDish> getAllDishesByOwnerName(String username) {
        log.trace("RestaurantService - getAllDishesByOwnerName");

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(format(NOT_FOUND, username)));
        log.trace("Finding all restaurants owned by {}", username);
        Set<Restaurant> restaurants = restaurantRepo.findByUserUuid(user.getUuid());

        Set<Dish> dishes = new HashSet<>();
        restaurants.forEach(rest -> dishes.addAll(rest.getDishes()));
        log.trace("Restaurants found {}", restaurants);
        log.trace("Dishes found {}", dishes);
        return toCreateDishSetFrom(dishes);
    }

    public Restaurant getRestaurantById(UUID uuid) {
        return restaurantRepo.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException(format("Restaurant not found for id %s", uuid)));
    }

    public VwRestaurant registerRestaurant(TCreateRestaurant tCreateRestaurant, MultipartFile license, String username, String photoResourceUri) throws IOException {
        log.trace("Restaurant Service - registerRestaurant");

        log.trace("Fetching {}", username);
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException("The restaurant owner's username is a required field");
        }

        Restaurant restaurant = toRestaurantFrom(tCreateRestaurant, user.get());

        // todo add file resource uri
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

    public VwRestaurant createRestaurant(TCreateRestaurant tCreateRestaurant, String username) {
        log.trace("Restaurant Service - TCreateRestaurant {}", tCreateRestaurant);

        log.trace("Fetching {}", username);
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {

            log.trace("Found {}, creating restaurant", user.get().getUsername());
            Restaurant restaurant = toRestaurantFrom(tCreateRestaurant, user.get());
            restaurant = restaurantRepo.save(restaurant);
            return toRestaurantViewFrom(restaurant);
        }
        throw new UserNotFoundException(format("Failed to create Restaurant. %s is not valid user", username));
    }

    public VwRestaurant editRestaurant(String restaurantJson, MultipartFile restaurantPhoto) throws JsonProcessingException {
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

    public VwDish createDish(String dishJson, MultipartFile photoFile, String photoResourceUri) {
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


        Dish dish = toDishFrom(dto);
        log.trace("done converting dto to dish item {}", dish);

        try {
            // process the image
            log.trace("start processing photo");
            Photo photo = documentService.processPhoto(photoFile, PhotoType.DISH, photoResourceUri);

            restaurant.addDish(dish);
            restaurant.addPhoto(photo);

            log.trace("done adding photo relationships ...");
            photo = photoRepo.saveAndFlush(photo);

            dish.addPhoto(photo);
            dish = dishRepo.saveAndFlush(dish);
            log.trace("done saving the dish {} ", dish);

            // save / update the restaurant entity
            restaurantRepo.save(restaurant);
            log.trace("Done updating the restaurant .. ");

        } catch(IOException ex) {
            log.error(format("Unable to process %s", photoFile.getName()));
        }

        log.trace("Saving dish item");
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
