package com.restaurant.domain.mapper;

import com.restaurant.domain.dto.request.TRegisterUser;
import com.restaurant.domain.dto.response.VwUser;
import com.restaurant.domain.dto.response.VwVerified;
import com.restaurant.domain.model.User;


public class UserMapper {

    private UserMapper() {}

    public static User toUserFrom(TRegisterUser tRegisterUser) {
        return User.builder()
                .username(tRegisterUser.userName())
                .firstName(tRegisterUser.firstName())
                .lastName(tRegisterUser.lastName())
                .password(tRegisterUser.password())
                .build();
    }

    public static VwUser toCreateUserFrom(User user) {
        return new VwUser(
                user.getUuid(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    public static VwVerified toUserVerifiedResponseFrom(User user) {
        return new VwVerified(
                user.getUuid(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName()
        );
    }


}
