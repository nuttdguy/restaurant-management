package com.restaurant.domain.dto.response;

import java.util.UUID;

public record VwDish(
        Long itemId,
        String itemName,
        String description,
        Float price,
        UUID restaurantId
) {
}
