package com.restaurant.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record VwUser(
        UUID userId,
        String username,
        String firstName,
        String lastName
) {

    public VwUser(UUID userId) {
        this(userId, null, null, null);
    }

    public VwUser(UUID userId, String username) {
        this(userId, username, null, null);
    }

}
