package com.restaurant.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record VwRefreshToken(
        String accessToken,
        String refreshToken,
        String tokenType
) {
    public VwRefreshToken {
        if (tokenType == null) {
            tokenType = "Bearer ";
        }
    }
}
