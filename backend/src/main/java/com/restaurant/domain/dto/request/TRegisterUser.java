package com.restaurant.domain.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

public record TRegisterUser(
    @NotBlank @Email String username,
    String firstName,
    String lastName,
    @NotBlank String password,
    @NotBlank String confirmPassword,
    Set<String> authorities)
{
    public TRegisterUser {
        if (authorities == null) {
            authorities = new HashSet<>();
        }
    }

    public TRegisterUser(
            String username,
            String firstName,
            String lastName,
            String password,
            String confirmPassword
    ){
        this(username, firstName, lastName, password, confirmPassword, new HashSet<>());
    }

}
