package com.restaurant.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.restaurant.domain.model.Photo;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record VwRestaurant(
        UUID id,
        String name,
        String url,
        String phone,
        VwAddress address,
        Set<Photo> photos,
        Photo photo,
        VwUser user
) {

}
