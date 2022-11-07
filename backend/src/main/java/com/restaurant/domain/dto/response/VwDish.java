package com.restaurant.domain.dto.response;

import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record VwDish(
        Long id,
        String name,
        String category,
        String description,
        String ingredients,
        String tags,
        Set<VwPhoto> photos,
        Float price,
        UUID restaurantId
) {
}
