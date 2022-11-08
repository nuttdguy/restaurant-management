package com.restaurant.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
        Set<VwPhoto> photos,
        VwUser user
) {

}
