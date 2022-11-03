package com.restaurant.domain.dto.response;

import java.util.UUID;

public record VwVerified(
        UUID id,
        String username,
        String firstName,
        String lastName
) {
}
