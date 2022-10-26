package com.restaurant.domain.dto.response;

import java.util.UUID;

public record GetRestaurantByName(
        UUID id,
        String restaurantName,
        String address1,
        String city,
        String state,
        String zip,
        String owner,
        UUID ownerId
) {
}
