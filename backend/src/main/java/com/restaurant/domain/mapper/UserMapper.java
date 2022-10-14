package com.restaurant.domain.mapper;

import com.restaurant.domain.dto.request.RegisterRequest;
import com.restaurant.domain.dto.response.CreateUserResponse;
import com.restaurant.domain.dto.response.UserVerifiedResponse;
import com.restaurant.domain.model.Role;
import com.restaurant.domain.model.RoleType;
import com.restaurant.domain.model.User;
import org.springframework.beans.BeanUtils;

public class UserMapper {

    public static User toUserFrom(RegisterRequest registerRequest) {
        User user = new User();
        BeanUtils.copyProperties(registerRequest, user);
        Role role1 = new Role(RoleType.PUBLIC_USER);
        Role role2 = new Role(RoleType.REGISTERED_USER);
        user.getAuthorities().add(role1);
        user.getAuthorities().add(role2);
        return user;
    }

    public static CreateUserResponse toCreateUserFrom(User user) {
        return new CreateUserResponse(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    public static UserVerifiedResponse toUserVerifiedResponseFrom(User user) {
        return new UserVerifiedResponse(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName()
        );
    }


}
