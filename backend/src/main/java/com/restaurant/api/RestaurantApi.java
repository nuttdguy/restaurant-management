package com.restaurant.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.domain.dto.request.TCreateRestaurant;
import com.restaurant.domain.dto.request.TEditDish;
import com.restaurant.domain.dto.response.VwRestaurant;
import com.restaurant.domain.model.RoleType;
import com.restaurant.service.RestaurantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.security.Principal;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("restaurant")
@AllArgsConstructor
public class RestaurantApi {

    private final RestaurantService restaurantService;

    @GetMapping
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> getRestaurantsByOwner(Principal principal) {
        log.trace("RestaurantApi - getRestaurantsByOwner {}", principal.getName());

        return ResponseEntity.ok(restaurantService.getRestaurantsByOwnerName(principal.getName()));
    }

    @GetMapping("/name/{name}")
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> getRestaurants(@PathVariable("name") String restaurantName) {
        Set<VwRestaurant> data = restaurantService.getRestaurantsByName(restaurantName);
        log.trace("fetched restaurants by restaurant name {}", data);

        return ResponseEntity.ok(data);
    }

    @GetMapping("/id/{uuid}")
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> getRestaurantById(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(uuid));
    }

    @PostMapping(value = "/create", consumes = { MediaType.APPLICATION_JSON_VALUE})
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> registerRestaurant(@RequestBody TCreateRestaurant tCreateRestaurant) throws IOException {
        log.trace("Restaurant Api - registerRestaurant - json");
        return ResponseEntity.ok(restaurantService.registerRestaurant(tCreateRestaurant, null));
    }

    @PostMapping(value = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> registerRestaurant(@RequestPart("data") String data,
                                                   @RequestPart("license") MultipartFile licenseDocument,
                                                     @RequestPart("image") MultipartFile image) throws IOException {
        log.trace("Restaurant Api - registerRestaurant - multi-part");

        ObjectMapper objectMapper = new ObjectMapper();
        TCreateRestaurant tCreateRestaurant = objectMapper.readValue(data, TCreateRestaurant.class);
        return ResponseEntity.ok(restaurantService.registerRestaurant(tCreateRestaurant, licenseDocument));
    }

    @PutMapping(value = "/edit", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> editRestaurant(@RequestPart("data") String data,
                                                @RequestPart("image") MultipartFile photo) throws JsonProcessingException {
        log.trace("Restaurant Api - editRestaurant");
        return ResponseEntity.ok(restaurantService.editRestaurant(data, photo));
    }


    // DISH ITEM MAPPINGS
    @GetMapping("/dishes")
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> getAllDishes(Principal principal) {
        log.trace("Restaurant Api - getAllDishes");

        return ResponseEntity.ok(restaurantService.getAllDishesByOwnerName(principal.getName()));
    }

    @GetMapping("/{restaurantId}/dish")
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> getDishes(@PathVariable("restaurantId") UUID uuid) {
        log.trace("RestaurantApi - getDishes");
        return ResponseEntity.ok(restaurantService.getRestaurantItems(uuid));
    }

    @PostMapping(value = "/dish/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> createDish(@RequestPart("data") String dish,
                                            @RequestPart("image") MultipartFile image) {

        log.trace("RestaurantApi - createDish");
        return ResponseEntity.ok(restaurantService.createDish(dish, image));
    }

    @PutMapping(value = "/dish/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> editDish(@RequestPart("data") TEditDish TEditDish) {
        log.trace("RestaurantApi - TEditDish");

        // TODO - made changes, fix this
        return ResponseEntity.ok(restaurantService.editDish(UUID.randomUUID(), TEditDish));
    }

    @DeleteMapping("/{restaurantId}")
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> removeRestaurant(@PathVariable("restaurantId") UUID restaurantId) {
        log.trace("RestaurantApi - removeRestaurant");
        return ResponseEntity.ok(restaurantService.removeRestaurant(restaurantId));
    }

    @DeleteMapping("/dish/{dishId}")
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> removeDish(@PathVariable("dishId") Long dishId) {
        log.trace("RestaurantApi - removeDish");
        return ResponseEntity.ok(restaurantService.removeDish(dishId));
    }

}
