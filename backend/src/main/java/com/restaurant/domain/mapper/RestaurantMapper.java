package com.restaurant.domain.mapper;

import com.restaurant.domain.dto.request.CreateRestaurantTo;
import com.restaurant.domain.dto.request.UpdateRestaurantTo;
import com.restaurant.domain.dto.response.CreateRestaurantResponse;
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

    public static CreateRestaurantResponse toAddEditRestaurantResponseFrom(Restaurant restaurant) {
        return new CreateRestaurantResponse(
                restaurant.getUuid(),
                restaurant.getName(),
                restaurant.getUrl(),
                restaurant.getCategory(),
                restaurant.getDescription(),
                restaurant.getAddress1(),
                restaurant.getAddress2(),
                restaurant.getCity(),
                restaurant.getState(),
                restaurant.getZip(),
                restaurant.getPhone(),
                restaurant.getCountry(),
                restaurant.getImgUrl(),
                restaurant.getUser().getUsername()
        );
    }

    public static Restaurant updateRestaurantProperties(UpdateRestaurantTo request, Restaurant old) {
        Restaurant updated = new Restaurant();
        BeanUtils.copyProperties(request, updated);
        BeanUtils.copyProperties(old, updated); // transfer old
        log.trace("Transferred request properties {}", updated);
        return updated;
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
