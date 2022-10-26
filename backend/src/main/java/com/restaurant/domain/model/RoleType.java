package com.restaurant.domain.model;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class RoleType {

    public static final String PUBLIC_USER = "PUBLIC_USER";
    public static final String REGISTERED_USER = "REGISTERED_USER";
    public static final String RESTAURANT_OPERATOR = "RESTAURANT_OPERATOR";
    public static final String ADMIN = "ADMIN";

    private String name;

}
