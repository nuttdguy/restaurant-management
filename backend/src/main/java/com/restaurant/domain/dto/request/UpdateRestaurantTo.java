package com.restaurant.domain.dto.request;

import java.util.UUID;

public record UpdateRestaurantTo(
        UUID id,
        String restaurantName,
        String url,
        String category,
        String description,
        String address1,
        String address2,
        String city,
        String state,
        String zip,
        String phone,
        String country,
        String image,
        String username
) {
}
