package com.restaurant.domain.dto.response;

public record UserVerifiedResponse(
        String username,
        String firstName,
        String lastName
) {
}
