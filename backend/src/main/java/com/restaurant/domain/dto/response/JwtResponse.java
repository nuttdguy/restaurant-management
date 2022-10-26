package com.restaurant.domain.dto.response;


import java.util.UUID;

public record JwtResponse(
        String type,
        String token,
        String refreshToken,
        String username,
        UUID id) {

    public JwtResponse {
        if (type == null) {
            type = "Bearer ";
        }
    }
}
