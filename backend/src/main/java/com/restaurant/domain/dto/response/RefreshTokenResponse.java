package com.restaurant.domain.dto.response;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken,
        String tokenType
) {
    public RefreshTokenResponse {
        if (tokenType == null) {
            tokenType = "Bearer ";
        }
    }
}
