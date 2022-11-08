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
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.Set;
import java.util.UUID;

import static java.lang.String.format;

@Slf4j
@RestController
@RequestMapping("restaurant")
@AllArgsConstructor
public class RestaurantApi {

    private static final String URL_FORMAT_STRING = "http://%s:%s%s%s";
    private final RestaurantService restaurantService;

    @GetMapping
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> getRestaurantsByUsername(Principal principal) {
        log.trace("RestaurantApi - getRestaurantsByOwner {}", principal.getName());

        return ResponseEntity.ok(restaurantService.getRestaurantsByUsername(principal.getName()));
    }

    @GetMapping("/name/{name}")
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> getRestaurantsByName(@PathVariable("name") String restaurantName) {
        log.trace("RestaurantApi - getRestaurants {}", restaurantName);

        Set<VwRestaurant> data = restaurantService.getRestaurantsByName(restaurantName);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/id/{uuid}")
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> getRestaurantById(@PathVariable("uuid") UUID uuid) {
        log.trace("RestaurantApi - getRestaurantById {}", uuid);

        return ResponseEntity.ok(restaurantService.getRestaurantById(uuid));
    }


    @PostMapping(value = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> registerRestaurant(@RequestPart("data") String data,
                                                     @RequestPart("license") MultipartFile licenseDocument,
                                                     @RequestPart("image") MultipartFile image,
                                                     Principal principal,
                                                     HttpServletRequest httpServletRequest) throws IOException {
        log.trace("Restaurant Api - registerRestaurant - multi-part");

        String photoResourceUri = format(URL_FORMAT_STRING,
                httpServletRequest.getServerName(),
                httpServletRequest.getServerPort(),
                httpServletRequest.getContextPath());

        ObjectMapper objectMapper = new ObjectMapper();
        TCreateRestaurant tCreateRestaurant = objectMapper.readValue(data, TCreateRestaurant.class);
        return ResponseEntity.ok(restaurantService
                .registerRestaurant(tCreateRestaurant, licenseDocument, principal.getName(), photoResourceUri));
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

        return ResponseEntity.ok(restaurantService.getAllDishesByUsername(principal.getName()));
    }

    @GetMapping("/{restaurantId}/dish")
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> getDishes(@PathVariable("restaurantId") UUID uuid) {
        log.trace("RestaurantApi - getDishes");

        return ResponseEntity.ok(restaurantService.getDishesByRestaurantId(uuid));
    }

    @PostMapping(value = "/dish/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> createDish(@RequestPart("data") String dish,
                                            @RequestPart("image") MultipartFile image,
                                             HttpServletRequest httpServletRequest) {

        String photoResourceUri = format(URL_FORMAT_STRING,
                httpServletRequest.getServerName(),
                httpServletRequest.getServerPort(),
                httpServletRequest.getContextPath(),
                "/images");

        log.trace("RestaurantApi - createDish");
        return ResponseEntity.ok(restaurantService.createDish(dish, image, photoResourceUri));
    }

    @PutMapping(value = "/dish/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RolesAllowed({RoleType.REGISTERED_USER, RoleType.RESTAURANT_OPERATOR})
    public ResponseEntity<Object> editDish(@RequestPart("data") TEditDish tEditDish) {
        log.trace("RestaurantApi - editDish");

        return ResponseEntity.ok(restaurantService.editDish(UUID.randomUUID(), tEditDish));
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
