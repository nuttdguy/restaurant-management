package com.restaurant.domain.dto.response;

public record CreateUserResponse(
        String username,
        String firstName,
        String lastName
) {
}
