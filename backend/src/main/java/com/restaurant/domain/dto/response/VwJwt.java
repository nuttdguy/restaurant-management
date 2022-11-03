package com.restaurant.domain.dto.response;


import java.util.UUID;

public record VwJwt(
        String accessToken,
        String refreshToken,
        String username,
        UUID userId)
{

}
