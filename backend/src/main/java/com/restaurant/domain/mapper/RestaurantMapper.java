package com.restaurant.domain.mapper;

import com.restaurant.domain.dto.request.TCreateRestaurant;
import com.restaurant.domain.dto.request.TEditRestaurant;
import com.restaurant.domain.dto.response.RestaurantResponse;
import com.restaurant.domain.dto.response.VwAddress;
import com.restaurant.domain.dto.response.VwRestaurant;
import com.restaurant.domain.dto.response.VwUser;
import com.restaurant.domain.model.Restaurant;
import com.restaurant.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

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

    public static RestaurantResponse toRestaurantViewFrom(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getUuid(),
                restaurant.getName(),
                restaurant.getAlias(),
                restaurant.getUrl(),
                restaurant.getPhone(),
                restaurant.getCategory(),
                restaurant.getDescription(),
                restaurant.getAddress1(),
                restaurant.getAddress2(),
                restaurant.getCity(),
                restaurant.getState(),
                restaurant.getZip(),
                restaurant.getCountry(),
                restaurant.getPhoto()
        );
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
                restaurant.getPhone(),
                new VwAddress(
                        restaurant.getAddress1(),
                        restaurant.getAddress2(),
                        restaurant.getCity(),
                        restaurant.getState(),
                        restaurant.getZip()),
                new VwUser(
                        restaurant.getUser().getUuid(),
                        restaurant.getName())
        )).collect(Collectors.toSet());
    }

}
