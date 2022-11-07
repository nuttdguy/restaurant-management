package com.restaurant.domain.mapper;

import com.restaurant.domain.dto.request.TCreateRestaurant;
import com.restaurant.domain.dto.request.TEditRestaurant;
import com.restaurant.domain.dto.response.*;
import com.restaurant.domain.model.Photo;
import com.restaurant.domain.model.PhotoType;
import com.restaurant.domain.model.Restaurant;
import com.restaurant.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class RestaurantMapper {

    private RestaurantMapper() {}

    public static Restaurant toRestaurantFrom(TCreateRestaurant tCreateRestaurant, User user) {
        Restaurant restaurant = new Restaurant();
        BeanUtils.copyProperties(tCreateRestaurant, restaurant);
        restaurant.setName(tCreateRestaurant.restaurantName());
        restaurant.setActive(true);
        restaurant.setHasLicense(true);
        restaurant.setUser(user);
        return restaurant;
    }

    public static VwRestaurant toRestaurantViewFrom(Restaurant restaurant) {
        return VwRestaurant.builder()
                .id(restaurant.getUuid())
                .name(restaurant.getName())
                .url(restaurant.getUrl())
                .phone(restaurant.getPhone())
                .address(new VwAddress(
                        restaurant.getAddress1(),
                        restaurant.getAddress2(),
                        restaurant.getCity(),
                        restaurant.getState(),
                        restaurant.getZip()))
                .photos(null)
                .photo(restaurant.getPhotos().stream()
                        .filter(e->e.getPhotoType().equals(PhotoType.PRIMARY))
                        .findFirst().orElse(null))
                .user(new VwUser(
                    restaurant.getUser().getUuid(),
                    restaurant.getUser().getUsername()))
                .build();
    }

    public static Restaurant updateRestaurantProperties(TEditRestaurant updateData, Restaurant old) {
        BeanUtils.copyProperties(updateData, old);
        log.trace("Transferred request properties {}", old);
        return old;
    }

    public static Set<VwRestaurant> toGetRestaurantByNameFrom(Set<Restaurant> restaurants) {
        return restaurants.stream().map(restaurant -> new VwRestaurant(
                restaurant.getUuid(),
                restaurant.getName(),
                restaurant.getUrl(),
                restaurant.getPhone(),
                new VwAddress(
                        restaurant.getAddress1(),
                        restaurant.getAddress2(),
                        restaurant.getCity(),
                        restaurant.getState(),
                        restaurant.getZip()),
                null,
                restaurant.getPhotos().stream()
                        .filter(e->e.getPhotoType().equals(PhotoType.PRIMARY))
                        .findFirst().orElse(null),
                new VwUser(
                        restaurant.getUser().getUuid(),
                        restaurant.getUser().getUsername())
        )).collect(Collectors.toSet());
    }

}
