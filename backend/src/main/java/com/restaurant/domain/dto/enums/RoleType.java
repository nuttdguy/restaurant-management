package com.restaurant.domain.dto.enums;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class RoleType {

    public static final String PUBLIC_USER = "ROLE_PUBLIC_USER";
    public static final String REGISTERED_USER = "ROLE_REGISTERED_USER";
    public static final String RESTAURANT_OPERATOR = "ROLE_RESTAURANT_OPERATOR";
    public static final String ADMIN = "ROLE_ADMIN";

    private String name;

}
