package com.restaurant.domain.dto.request;

import lombok.Builder;


@Builder
public record TCreateUser(
    String username,
    String firstName,
    String lastName,
    String password
) {

    public TCreateUser(String username, String password) {
        this(username, null, null, password);
    }

}
