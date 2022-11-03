package com.restaurant.domain.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record VwRestaurant(
        UUID id,
        String restaurantName,
        String phone,
        VwAddress address,
        VwUser user
) {

    public VwRestaurant() {
        this(null, null, null, null, null);
    }

    public VwRestaurant(UUID id) {
        this(id, null, null, null, null);
    }

    public VwRestaurant(UUID id, String restaurantName, String phone) {
        this(id, restaurantName, phone, null, null);
    }

    public VwRestaurant(UUID id, String restaurantName, String phone, VwAddress address) {
        this(id, restaurantName, phone, address, null);
    }

}
