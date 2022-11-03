package com.restaurant.domain.dto.response;

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
