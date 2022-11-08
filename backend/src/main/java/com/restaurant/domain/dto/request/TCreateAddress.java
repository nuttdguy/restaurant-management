package com.restaurant.domain.dto.request;

public record TCreateAddress(
        String address1,
        String address2,
        String city,
        String state,
        String zip
) {

}
