package com.restaurant.domain.dto.request;

import lombok.Builder;

@Builder
public record TCreateCategory(
        String name,
        String desc,
        String tags
) {
}
