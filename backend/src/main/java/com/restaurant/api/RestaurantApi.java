package com.restaurant.api;

import com.restaurant.domain.dto.request.CreateDishTo;
import com.restaurant.domain.dto.request.CreateRestaurantTo;
import com.restaurant.domain.dto.request.EditDishTo;
import com.restaurant.domain.dto.request.UpdateRestaurantTo;
import com.restaurant.domain.dto.response.GetRestaurantByName;
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

    @GetMapping("/owner/{username}")
    public ResponseEntity<Object> getRestaurantsByOwner(@PathVariable("username") String username) {
        log.trace("fetched restaurants by name of owner {}", username);
        return ResponseEntity.ok(restaurantService.getRestaurantsByOwnerName(username));
    }

//    @GetMapping("/{restaurantName}")
    public ResponseEntity<Object> getRestaurants(@PathVariable("restaurantName") String restaurantName) {
        Set<GetRestaurantByName> data = restaurantService.getRestaurantsByName(restaurantName);
        log.trace("fetched restaurants by restaurant name {}", data);

        return ResponseEntity.ok(data);
    }

    @GetMapping("/id/{uuid}")
    public ResponseEntity<Object> getRestaurantById(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(uuid));
    }

//    @PostMapping("/create")
//    public ResponseEntity<Object> createRestaurant(@RequestBody CreateRestaurantTo createRestaurantTo) {
//        log.trace("Restaurant Api - createRestaurant");
//        return ResponseEntity.ok(restaurantService.createRestaurant(createRestaurantTo));
//    }

    @PostMapping(value = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Object> registerRestaurant(@RequestPart("data") String data,
                                                   @RequestPart("file") MultipartFile licenseDocument) throws IOException {
        log.trace("Restaurant Api - registerRestaurant");
        return ResponseEntity.ok(restaurantService.registerRestaurant(data, licenseDocument));
    }

    @PutMapping("/edit")
    public ResponseEntity<Object> editRestaurant(@RequestBody UpdateRestaurantTo updateRestaurantTo) {
        log.trace("Restaurant Api - editRestaurant");
        return ResponseEntity.ok(restaurantService.editRestaurant(updateRestaurantTo));
    }

    @GetMapping("/{restaurantId}/items")
    public ResponseEntity<Object> getRestaurantItems(@PathVariable("restaurantId") UUID uuid) {
        log.trace("RestaurantApi - getRestaurantItems");
        return ResponseEntity.ok(restaurantService.getRestaurantItems(uuid));
    }

    @PostMapping("/{restaurantId}/items/create")
    public ResponseEntity<Object> createRestaurantItem(@PathVariable("restaurantId") UUID restaurantId,
                                                       @RequestBody CreateDishTo createDishTo) {
        log.trace("RestaurantApi - createRestaurantItem");
        return ResponseEntity.ok(restaurantService.createItem(restaurantId, createDishTo));
    }

    @PutMapping("/{restaurantId}/items/{itemId}/edit")
    public ResponseEntity<Object> editRestaurantItem(@PathVariable("restaurantId") UUID restaurantId,
                                                     @PathVariable("itemId") Long itemId,
                                                     @RequestBody EditDishTo editDishTo) {
        log.trace("RestaurantApi - editRestaurantItem");
        return ResponseEntity.ok(restaurantService.editRestaurantItem(restaurantId, itemId, editDishTo));
    }

    @DeleteMapping("/{restaurantId}/remove")
    public ResponseEntity<Object> removeRestaurant(@PathVariable("restaurantId") UUID restaurantId) {
        log.trace("RestaurantApi - removeRestaurant");
        return ResponseEntity.ok(restaurantService.removeRestaurant(restaurantId));
    }

    @DeleteMapping("/{restaurantId}/items/{itemId}/remove")
    public ResponseEntity<Object> removeRestaurantItem(@PathVariable("restaurantId") UUID restaurantId,
                                                       @PathVariable("itemId") Long itemId) {
        log.trace("RestaurantApi - removeRestaurantItem");
        return ResponseEntity.ok(restaurantService.removeRestaurantItem(itemId));
    }

}
