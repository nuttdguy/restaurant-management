package com.restaurant.domain.dto.response;

import java.util.UUID;

public record CreateDishResponse(
        Long itemId,
        String itemName,
        String description,
        Float price,
        UUID restaurantId
) {
}
