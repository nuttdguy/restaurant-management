package com.restaurant.domain.dto.response;

import java.util.UUID;

public record RestaurantResponse(
        UUID id,
        String name,
        String alias,
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
        String photo
) {

}
