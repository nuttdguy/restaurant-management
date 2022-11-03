package com.restaurant.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public record TEditRestaurant(
        UUID id,
        String restaurantName,
        @JsonIgnore String alias,
        String url,
        String phone,
        String category,
        String description,
        String address1,
        String address2,
        String city,
        String state,
        String zip,
        String country,
        @JsonIgnore String image,
        @JsonIgnore String username
) {
}
