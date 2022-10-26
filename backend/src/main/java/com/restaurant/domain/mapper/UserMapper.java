package com.restaurant.domain.mapper;

import com.restaurant.domain.dto.request.RegisterUserTo;
import com.restaurant.domain.dto.response.CreateUserResponse;
import com.restaurant.domain.dto.response.UserVerifiedResponse;
import com.restaurant.domain.model.User;


public class UserMapper {

    private UserMapper() {}

    public static User toUserFrom(RegisterUserTo registerUserTo) {
        User user = new User();
        user.setUsername(registerUserTo.username());
        user.setFirstName(registerUserTo.firstName());
        user.setLastName(registerUserTo.lastName());
        user.setPassword(registerUserTo.password());
        return user;
    }

    public static CreateUserResponse toCreateUserFrom(User user) {
        return new CreateUserResponse(
                user.getUuid(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    public static UserVerifiedResponse toUserVerifiedResponseFrom(User user) {
        return new UserVerifiedResponse(
                user.getUuid(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName()
        );
    }


}
