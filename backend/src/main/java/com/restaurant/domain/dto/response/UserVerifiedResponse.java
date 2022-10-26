package com.restaurant.domain.dto.response;

import java.util.UUID;

public record UserVerifiedResponse(
        UUID id,
        String username,
        String firstName,
        String lastName
) {
}
