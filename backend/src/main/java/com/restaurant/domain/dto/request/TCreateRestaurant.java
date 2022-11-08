package com.restaurant.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Builder
@JsonIgnoreProperties({"license", "image"})
public record TCreateRestaurant(
        UUID id,
        @NotBlank String restaurantName,
        String alias,
        String phone,
        String url,
        String category,
        String description,
        String address1,
        String address2,
        String city,
        String state,
        String zip,
        String country
//        @Nullable String username
) {

}
