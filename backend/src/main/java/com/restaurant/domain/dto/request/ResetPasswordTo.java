package com.restaurant.domain.dto.request;

import javax.validation.constraints.NotBlank;

public record ResetPasswordTo(
        @NotBlank String password,
        @NotBlank String confirmPassword
) {
}
