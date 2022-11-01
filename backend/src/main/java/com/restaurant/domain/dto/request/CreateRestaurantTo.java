package com.restaurant.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public record CreateRestaurantTo(
        @JsonIgnore UUID id,
        @NotBlank String restaurantName,
        String alias,
        String url,
        String category,
        String description,
        String address1,
        String address2,
        String city,
        String state,
        String zip,
        String phone,
        String country,
        @JsonIgnore String license,
        @JsonIgnore String image,
        String username
) {

}
