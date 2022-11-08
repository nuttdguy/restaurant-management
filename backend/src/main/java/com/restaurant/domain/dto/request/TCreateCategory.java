package com.restaurant.domain.dto.request;

public record TCreateCategory(
        String name,
        String desc,
        String tags
) {
}
