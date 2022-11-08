package com.restaurant.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record VwJwt(
        String accessToken,
        String refreshToken,
        String username,
        UUID userId)
{

}
