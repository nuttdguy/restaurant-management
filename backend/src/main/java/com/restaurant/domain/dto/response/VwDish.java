package com.restaurant.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
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
