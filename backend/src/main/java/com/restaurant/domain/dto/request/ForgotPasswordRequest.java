package com.restaurant.domain.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public record ForgotPasswordRequest(
        @NotNull @Email String username
) {

}
