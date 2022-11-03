package com.restaurant.domain.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public record TLogin(
        @NotNull @Email String username,
        @NotNull String password) { }
