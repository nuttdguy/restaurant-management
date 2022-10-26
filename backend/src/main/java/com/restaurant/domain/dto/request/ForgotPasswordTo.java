package com.restaurant.domain.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public record ForgotPasswordTo(
        @NotNull @Email String username
) {

}
