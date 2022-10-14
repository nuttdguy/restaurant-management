package com.restaurant.domain.dto.request;

import javax.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank String refreshToken
) {
}
