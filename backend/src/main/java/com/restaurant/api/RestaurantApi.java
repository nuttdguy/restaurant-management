package com.restaurant.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.domain.dto.request.TCreateRestaurant;
import com.restaurant.domain.dto.request.TEditDish;
import com.restaurant.domain.dto.response.VwRestaurant;
import com.restaurant.service.RestaurantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("restaurant")
@AllArgsConstructor
public class RestaurantApi {

    private final RestaurantService restaurantService;

    // TODO MAY NEED TO MODIFY MAPPINGS AFTER IMPLEMENTING SPRING SECURITY ROLES
    // SINCE USERS ARE AUTHENTICATED BY THE ACCESS TOKEN AND USER DATA AVAILABLE
    // THROUGH THE SECURITY CONTEXT


    // this route will change after jwt filter is enabled
    @GetMapping("/owner/{username}")
    public ResponseEntity<Object> getRestaurantsByOwner(@PathVariable("username") String username) {
        log.trace("fetched restaurants by name of owner {}", username);
        return ResponseEntity.ok(restaurantService.getRestaurantsByOwnerName(username));
    }

    // this will be enabled when jwt filter is enabled
//    @GetMapping("/{restaurantName}")
    public ResponseEntity<Object> getRestaurants(@PathVariable("restaurantName") String restaurantName) {
        Set<VwRestaurant> data = restaurantService.getRestaurantsByName(restaurantName);
        log.trace("fetched restaurants by restaurant name {}", data);

        return ResponseEntity.ok(data);
    }

    @GetMapping("/id/{uuid}")
    public ResponseEntity<Object> getRestaurantById(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(uuid));
    }

    @PostMapping(value = "/create", consumes = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> registerRestaurant(@RequestBody TCreateRestaurant tCreateRestaurant) throws IOException {
        log.trace("Restaurant Api - registerRestaurant - json");
        return ResponseEntity.ok(restaurantService.registerRestaurant(tCreateRestaurant, null));
    }


    @PostMapping(value = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Object> registerRestaurant(@RequestPart("data") String data,
                                                   @RequestPart("license") MultipartFile licenseDocument,
                                                     @RequestPart("image") MultipartFile image) throws IOException {
        log.trace("Restaurant Api - registerRestaurant - multi-part");

        ObjectMapper objectMapper = new ObjectMapper();
        TCreateRestaurant tCreateRestaurant = objectMapper.readValue(data, TCreateRestaurant.class);
        return ResponseEntity.ok(restaurantService.registerRestaurant(tCreateRestaurant, licenseDocument));
    }

    @PutMapping(value = "/edit", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Object> editRestaurant(@RequestPart("data") String data,
                                                @RequestPart("image") MultipartFile photo) throws JsonProcessingException {
        log.trace("Restaurant Api - editRestaurant");
        return ResponseEntity.ok(restaurantService.editRestaurant(data, photo));
    }


    // this will change when jwt filter is enabled
    // DISH ITEM MAPPINGS
    @GetMapping("/owner/{username}/dishes")
    public ResponseEntity<Object> getAllDishes(@PathVariable("username") String username) {
        log.trace("Restaurant Api - getAllDishes");

        return ResponseEntity.ok(restaurantService.getAllDishesByOwnerName(username));
    }

    @GetMapping("/{restaurantId}/dish")
    public ResponseEntity<Object> getDishes(@PathVariable("restaurantId") UUID uuid) {
        log.trace("RestaurantApi - getDishes");
        return ResponseEntity.ok(restaurantService.getRestaurantItems(uuid));
    }

    @PostMapping(value = "/dish/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createDish(@RequestPart("data") String dish,
                                            @RequestPart("image") MultipartFile image) throws JsonProcessingException {

        log.trace("RestaurantApi - createDish");
        return ResponseEntity.ok(restaurantService.createDish(dish, image));
    }

    @PutMapping(value = "/dish/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> editDish(@RequestPart("data") TEditDish TEditDish) {
        log.trace("RestaurantApi - TEditDish");
        // TODO - made changes, fix this
        return ResponseEntity.ok(restaurantService.editDish(UUID.randomUUID(), TEditDish));
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<Object> removeRestaurant(@PathVariable("restaurantId") UUID restaurantId) {
        log.trace("RestaurantApi - removeRestaurant");
        return ResponseEntity.ok(restaurantService.removeRestaurant(restaurantId));
    }

    @DeleteMapping("/dish/{dishId}")
    public ResponseEntity<Object> removeDish(@PathVariable("dishId") Long dishId) {
        log.trace("RestaurantApi - removeDish");
        return ResponseEntity.ok(restaurantService.removeDish(dishId));
    }

}
