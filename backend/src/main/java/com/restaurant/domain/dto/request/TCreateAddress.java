package com.restaurant.domain.dto.request;

import lombok.Builder;

@Builder
public record TCreateAddress(
        String address1,
        String address2,
        String city,
        String state,
        String zip
) {

}
