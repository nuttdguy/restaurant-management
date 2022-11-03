package com.restaurant.domain.dto.response;

public record VwAddress(
        String address1,
        String address2,
        String city,
        String state,
        String zip
) {
}
