package com.restaurant.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record VwAddress(
        String address1,
        String address2,
        String city,
        String state,
        String zip
) {
}
