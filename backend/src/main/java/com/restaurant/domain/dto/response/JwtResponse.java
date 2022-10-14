package com.restaurant.domain.dto.response;

import java.util.List;

public record JwtResponse(
        String type,
        String token,
        String refreshToken,
        String username,
        List<String> roles) {

    public JwtResponse {
        if (type == null) {
            type = "Bearer ";
        }
    }
}
