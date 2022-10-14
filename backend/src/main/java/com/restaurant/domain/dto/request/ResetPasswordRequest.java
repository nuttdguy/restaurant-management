package com.restaurant.domain.dto.request;

import javax.validation.constraints.NotBlank;

public record ResetPasswordRequest(
        @NotBlank String password,
        @NotBlank String confirmPassword
) {
}
