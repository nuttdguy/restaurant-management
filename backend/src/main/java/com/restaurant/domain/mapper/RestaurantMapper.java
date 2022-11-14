package com.restaurant.domain.mapper;

import com.restaurant.domain.dto.request.TCreateRestaurant;
import com.restaurant.domain.dto.request.TEditRestaurant;
import com.restaurant.domain.dto.response.*;
import com.restaurant.domain.dto.enums.PhotoType;
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
                .address(VwAddress.builder()
                        .address1(restaurant.getAddress1())
                        .address2(restaurant.getAddress2())
                        .city(restaurant.getCity())
                        .state(restaurant.getState())
                        .zip(restaurant.getZip())
                        .build())
                .photos(restaurant.getPhotos().stream()
                        .filter(photo -> photo.getPhotoType().equals(PhotoType.PRIMARY))
                        .map(photo -> VwPhoto.builder()
                                .id(photo.getId())
                                .photoUrl(photo.getPhotoUrl())
                                .type(photo.getType())
                                .name(photo.getName())
                                .build())
                        .collect(Collectors.toSet()))
                .user(VwUser.builder()
                        .userId(restaurant.getUser().getUuid())
                        .username(restaurant.getUser().getUsername())
                        .build())
                .build();
    }

    public static List<VwRestaurant> toRestaurantListViewFrom(Set<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantMapper::toRestaurantViewFrom)
                .toList();
    }

    public static Set<VwRestaurant> toRestaurantSetViewFrom(Set<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantMapper::toRestaurantViewFrom)
                .collect(Collectors.toSet());
    }

    public static Restaurant updateRestaurantProperties(TEditRestaurant updateData, Restaurant old) {
        BeanUtils.copyProperties(updateData, old);
        log.trace("Transferred request properties {}", old);
        return old;
    }

}
