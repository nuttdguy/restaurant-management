package com.restaurant.domain.dto.response;

import java.util.UUID;

public record VwDish(
        Long id,
        String name,
        String category,
        String description,
        String ingredients,
        String tags,
//        String photoUrl,
        Float price,
        UUID restaurantId
) {
}
