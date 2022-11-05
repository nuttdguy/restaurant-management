package com.restaurant.domain.dto.request;

import javax.validation.constraints.NotBlank;

public record TPasswordReset(
        @NotBlank String password) {
}
