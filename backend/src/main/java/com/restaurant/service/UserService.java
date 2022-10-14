package com.restaurant.service;

import com.restaurant.domain.dto.request.RegisterRequest;
import com.restaurant.domain.dto.response.CreateUserResponse;
import com.restaurant.domain.dto.response.UserVerifiedResponse;
import com.restaurant.domain.mapper.UserMapper;
import com.restaurant.domain.model.RegistrationToken;
import com.restaurant.domain.model.Role;
import com.restaurant.domain.model.RoleType;
import com.restaurant.event.RegistrationEvent;
import com.restaurant.exception.TokenExpiredException;
import com.restaurant.repository.IUserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.restaurant.domain.model.User;
import com.restaurant.exception.AccountNotActiveException;
import com.restaurant.exception.UserExistsException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import java.time.Instant;
import java.util.UUID;

import static com.restaurant.domain.mapper.UserMapper.*;
import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final String REGISTER_URL_SUFFIX = "/user/verify/";
    private final TokenService tokenService;
    private final IUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public CreateUserResponse registerUser(RegisterRequest registerRequest,
                                           HttpServletRequest httpServletRequest) {
        log.trace("UserService - registerUser");

        if (!registerRequest.password().equals(registerRequest.confirmPassword())) {
            throw new ValidationException("Passwords do not match");
        }

        log.trace("Fetching a user by username or email, else create a new user");
        User user = userRepo.findByUsername(registerRequest.username())
                .orElseGet(() -> UserMapper.toUserFrom(registerRequest));

        log.trace("Encoding and setting the password");
        user.setPassword(passwordEncoder.encode(registerRequest.password()));

        log.trace("Checking if this is a new user and if this user account has been activated / verified");
        if (user.getUid() != null && !user.isEnabled()) {
            log.trace("User found, but has not been activated / enabled");
            throw new AccountNotActiveException("An account was found for "+user.getUsername()+" -> Please verify your email and activate your account");
        }

        log.trace("Checking if this user is already registered");
        if (user.getUid() != null && user.isEnabled()) {
            log.trace("User found and has already been activated {}", user.getUsername());
            throw new UserExistsException("User "+user.getUsername()+" already exists. Please login instead.");
        }

        log.trace("Registering the user");
        user = userRepo.saveAndFlush(user);

        if (!user.isEnabled()) {

            log.trace("Building and then email registration url");
            String url = constructRegisterUrl(httpServletRequest) + REGISTER_URL_SUFFIX ;

            log.trace("Publishing the registration event");
            // publish a new user registration event to
            applicationEventPublisher.publishEvent(new RegistrationEvent(user, url));
        }

        log.trace("Returning the user {}", user);
        return UserMapper.toCreateUserFrom(user);
    }

    @Transactional
    public UserVerifiedResponse verifyRegistration(String theToken) {
        log.trace("RegistrationService - verifyRegistrationToken");

        RegistrationToken registrationToken = tokenService.findRegisterTokenById(theToken);

        log.trace("Checking if the token is expired");
        verifyExpiration(registrationToken);

        log.trace("Extracting the user owning this registration token={}", theToken);
        User user = registrationToken.getUser();

        log.trace("Enabling and updating the user {}", user);
        user.setEnabled(true);
        user.getAuthorities().add(new Role(RoleType.REGISTERED_USER));
        userRepo.save(user);

        log.trace("Deleting the registration token {}", theToken);
        tokenService.deleteRegisterToken(registrationToken);
        return toUserVerifiedResponseFrom(user);
    }

    private RegistrationToken verifyExpiration(RegistrationToken registrationToken) {
        if (registrationToken.getExpiration().compareTo(Instant.now()) < 0) {
            tokenService.deleteRegisterToken(registrationToken);

            log.trace("The registration token is expired");
            throw new TokenExpiredException("The registration token is expired");

        }
        return registrationToken;
    }



    @Transactional
    public int deleteUser(String username) {
        userRepo.delete(userRepo
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(format("Delete failed because user %s was not found", username))));
        return 1;
    }

    @Transactional
    public int deleteUser(UUID uuid) {
        userRepo.delete(userRepo
                .findById(uuid)
                .orElseThrow(() -> new UsernameNotFoundException(format("Delete failed because user of id %s was not found", uuid))));
        return 1;
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(format("%s not found", username)));
    }

    private String constructRegisterUrl(HttpServletRequest request) {
        return "http://"+
                request.getServerName()+
                ":"+
                request.getServerPort()+
                request.getContextPath();
    }

}
