package com.restaurant.domain.mapper;

import com.restaurant.domain.dto.request.CreateRestaurantTo;
import com.restaurant.domain.dto.request.UpdateRestaurantTo;
import com.restaurant.domain.dto.response.RestaurantResponse;
import com.restaurant.domain.dto.response.GetRestaurantByName;
import com.restaurant.domain.model.Restaurant;
import com.restaurant.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class RestaurantMapper {

    private RestaurantMapper() {}

    public static Restaurant toRestaurantFrom(CreateRestaurantTo createRestaurantTo, User user) {
        Restaurant restaurant = new Restaurant();
        BeanUtils.copyProperties(createRestaurantTo, restaurant);
        restaurant.setName(createRestaurantTo.restaurantName());
        restaurant.setActive(true);
        restaurant.setHasLicense(true);
        restaurant.setUser(user);
        return restaurant;
    }

    public static RestaurantResponse toRestaurantResponse(Restaurant restaurant) {
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
                restaurant.getImg()
        );
    }

    public static Restaurant updateRestaurantProperties(UpdateRestaurantTo updateData, Restaurant old) {
        BeanUtils.copyProperties(updateData, old);
        log.trace("Transferred request properties {}", old);
        return old;
    }

    public static Set<GetRestaurantByName> toGetRestaurantByNameFrom(Set<Restaurant> restaurants) {
        return restaurants.stream().map(restaurant -> new GetRestaurantByName(
                restaurant.getUuid(),
                restaurant.getName(),
                restaurant.getAddress1(),
                restaurant.getCity(),
                restaurant.getState(),
                restaurant.getZip(),
                restaurant.getUser().getUsername(),
                restaurant.getUser().getUuid()
        )).collect(Collectors.toSet());
    }

}
