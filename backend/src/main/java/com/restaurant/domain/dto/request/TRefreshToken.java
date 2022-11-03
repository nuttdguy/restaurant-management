package com.restaurant.domain.dto.request;

import javax.validation.constraints.NotBlank;

public record TRefreshToken(
        @NotBlank String refreshToken
) {
}
