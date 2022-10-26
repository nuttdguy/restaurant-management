package com.restaurant.domain.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

public record RegisterUserTo(
    @NotBlank @Email String username,
    String firstName,
    String lastName,
    @NotBlank String password,
    @NotBlank String confirmPassword,
    Set<String> authorities)
{
    public RegisterUserTo {
        if (authorities == null) {
            authorities = new HashSet<>();
        }
    }

    public RegisterUserTo(
            String username,
            String firstName,
            String lastName,
            String password,
            String confirmPassword
    ){
        this(username, firstName, lastName, password, confirmPassword, new HashSet<>());
    }

}
