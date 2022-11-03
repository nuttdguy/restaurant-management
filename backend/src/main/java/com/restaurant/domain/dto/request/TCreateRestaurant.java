package com.restaurant.domain.dto.request;

import lombok.Builder;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

public record TCreateRestaurant(
        UUID id,
        @NotBlank String restaurantName,
        String phone,
        String url,
        String category,
        String description,
        TCreateAddress createAddress,
        TCreateLicense license,
        TCreatePhoto photo,
        TCreateUser user
) {
    @Builder
    public TCreateRestaurant { }

    public TCreateRestaurant(String restaurantName,
                             String phone,
                             String url,
                             String category,
                             String description,
                             TCreateAddress address) {
        this(null, restaurantName, phone, url, category, description, address, null, null, null);
    }

    public TCreateRestaurant(String restaurantName,
                             String phone,
                             String url,
                             String category,
                             String description,
                             TCreateAddress address,
                             TCreateLicense license,
                             TCreatePhoto photo) {
        this(null, restaurantName, phone, url, category, description, address, license, photo, null);
    }

    public TCreateRestaurant(String restaurantName,
                             String phone,
                             String url,
                             String category,
                             String description,
                             TCreateAddress address,
                             TCreateLicense license,
                             TCreatePhoto photo,
                             TCreateUser user) {
        this(null, restaurantName, phone, url, category, description, address, license, photo, user);
    }

}
