package com.restaurant.service;

import com.restaurant.domain.dto.request.TForgotPassword;
import com.restaurant.domain.dto.request.TRegisterUser;
import com.restaurant.domain.dto.request.TResetPassword;
import com.restaurant.domain.dto.response.VwLink;
import com.restaurant.domain.model.TokenType;
import com.restaurant.domain.model.UuidToken;
import com.restaurant.domain.model.Role;
import com.restaurant.event.SendEmailEvent;
import com.restaurant.exception.*;
import com.restaurant.repository.IRoleRepo;
import com.restaurant.repository.IUserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.restaurant.domain.model.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.restaurant.domain.mapper.UserMapper.*;
import static com.restaurant.exception.ExceptionMessage.*;
import static java.lang.String.format;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private static final String FORMAT_URL = "%s/%s";
    private final TokenService tokenService;
    private final IRoleRepo roleRepo;
    private final IUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public User loadUserByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(format(NOT_FOUND, username)));
    }

    public User findById(UUID id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(format(NOT_FOUND, id)));
    }

    public VwLink registerNewUser(TRegisterUser tRegisterUser, String urlLink) {
        log.trace("UserService - TRegisterUser");

        User newUser = createNewUserOrThrow(tRegisterUser);

        log.trace("Generating the registration token and publishing the email event");
        UuidToken token = createAndSaveToken(newUser, TokenType.REGISTRATION);

        publishEmailEventTo(newUser, urlLink, token.getToken(), token.getTokenType());
        log.trace("Async - done publishing async event");

        return new VwLink(format(FORMAT_URL, urlLink, token.getToken()));
    }

    public String verifyUserRegistration(UUID theToken) {
        log.trace("UserService - verifyRegistrationToken");

        UuidToken uuidToken = tokenService.findToken(theToken)
                .orElseThrow(() -> new NotFoundException(format(NOT_FOUND, theToken)));

        log.trace("Checking if the token is expired");
        if (uuidToken.getExpiration().compareTo(Instant.now()) < 0) {
            throw new ExpiredException(format(EXPIRED_TOKEN, uuidToken.getToken()));
        }

        log.trace("Extracting the user owning this registration token={}", theToken);
        User user = uuidToken.getUser();

        log.trace("Enabling and updating the user {}", user);
        user.setEnabled(true);
        List<Role> roles = roleRepo.findAll();
        user.getAuthorities().add(roles.get(0));
        user.getAuthorities().add(roles.get(1));
        userRepo.save(user);

        log.trace("Deleting the registration token {}", theToken);
        tokenService.deleteToken(uuidToken);
        return format("Thank you for verifying %s", user.getUsername());
    }

    public VwLink resendToken(UUID oldToken, String verifyURL) {
        log.trace("UserService - resendToken");

        UuidToken uuidToken = tokenService.findToken(oldToken)
                .orElseThrow(() -> new NotFoundException(format(NOT_FOUND, oldToken)));

        UUID newToken = UUID.randomUUID();
        log.trace("Updating the registration token {}", oldToken);
        uuidToken.setToken(newToken);
        uuidToken.setExpiration(Instant.now());
        tokenService.save(uuidToken);

        return new VwLink(format(FORMAT_URL, verifyURL, newToken));
    }

    public String forgotPassword(TForgotPassword tForgotPassword, String urlLink) {
        log.trace("User Service - forgotPassword");

        log.trace("Finding user by username " );
        User user = userRepo.findByUsername(tForgotPassword.username())
                .orElseThrow(() -> new NotFoundException(format(NOT_FOUND, tForgotPassword.username())));

        log.trace("Generating the password reset token and publishing the email event");
        UuidToken uuidToken = createAndSaveToken(user, TokenType.PASSWORD_RESET);

        publishEmailEventTo(user, urlLink, uuidToken.getToken(), uuidToken.getTokenType());
        log.trace("Async - done publishing async event");

        return "Reset link sent to your email";
    }

    public Object resetPassword(TResetPassword tResetPassword, String thePwdResetToken) {
        return null;
    }

    public String deleteUser(String username) {
        userRepo.delete(userRepo
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(format(DELETE_FAILURE, username))));
        return "Success";
    }

    public String deleteUser(UUID uuid) {
        userRepo.delete(userRepo.findById(uuid)
                .orElseThrow(() -> new UsernameNotFoundException(format(DELETE_FAILURE, uuid))));
        return "Success";
    }

    private UuidToken createAndSaveToken(User newUser, TokenType registration) {
        UUID uuid = UUID.randomUUID();
        UuidToken token = new UuidToken(newUser, uuid, registration);

        log.trace("Saving the user + registration token {}", token.getToken());
        token.setUser(newUser);
        userRepo.save(newUser);
        tokenService.save(token);

        log.trace("Done creating and saving {} token", token.getToken());
        return token;
    }

    private User createNewUserOrThrow(TRegisterUser tRegisterUser) {
        log.trace("UserService - extractUserThrowIfFound");

        if (!tRegisterUser.password().equals(tRegisterUser.confirmPassword())) {
            throw new ValidationException(format(VALIDATION_FAILURE, "password", tRegisterUser.password(), "must match"));
        }

        Optional<User> user = userRepo.findByUsername(tRegisterUser.username());
        log.trace("Checking if user is present {}", user);
        if (user.isPresent()) {
            if (!user.get().isEnabled()) {
                throw new NotActiveException(format(NOT_VERIFIED, tRegisterUser.username()));
            }
            throw new UserExistsException(format(ENTITY_EXISTS, tRegisterUser.username(), " Login instead."));
        }

        User newUser = toUserFrom(tRegisterUser);
        newUser.setPassword(passwordEncoder.encode(tRegisterUser.password()));
        return newUser;
    }

    private void publishEmailEventTo(User user, String urlLink, UUID token, TokenType tokenType) {
        log.trace("UserService - publishEmailEvent");
        applicationEventPublisher.publishEvent(new SendEmailEvent(user, urlLink, token, tokenType));
    }

}
