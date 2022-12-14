package com.restaurant.service;

import com.restaurant.domain.dto.enums.RoleType;
import com.restaurant.domain.dto.enums.TokenType;
import com.restaurant.domain.dto.request.TPasswordForgot;
import com.restaurant.domain.dto.request.TRegisterUser;
import com.restaurant.domain.dto.request.TPasswordReset;
import com.restaurant.domain.dto.response.VwLink;
import com.restaurant.domain.model.*;
import com.restaurant.event.SendEmailEvent;
import com.restaurant.exception.*;
import com.restaurant.repository.IRoleRepo;
import com.restaurant.repository.IUserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

import java.time.Instant;
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

    private static final String URL_STRING = "%s/%s";
    private final TokenService tokenService;
    private final IUserRepo userRepo;
    private final IRoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public User loadUserByUsername(String username) {
        log.trace("UserService - loadUserByUsername");
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(format(NOT_FOUND, username)));
    }

    public User findById(UUID id) {
        log.trace("UserService - findById");
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(format(NOT_FOUND, id)));
    }

    public VwLink registerNewUser(TRegisterUser tRegisterUser, String urlLink) {
        log.trace("UserService - registerNewUser");

        User newUser = createNewUserOrThrow(tRegisterUser);

        log.trace("Generating the registration token and publishing the email event");
        UniqueToken token = createAndSaveToken(newUser, TokenType.REGISTRATION);

//        publishEmailEventTo(newUser, urlLink, token.getToken(), token.getTokenType());
        log.trace("Async - done publishing async event");

        return new VwLink(format(URL_STRING, urlLink, token.getToken()));
    }

    public String verifyUserRegistration(UUID theToken) {
        log.trace("UserService - verifyRegistrationToken");

        UniqueToken uniqueToken = tokenService.findByToken(theToken);
//                .orElseThrow(() -> new NotFoundException(format(NOT_FOUND, theToken)));

        if (uniqueToken.getExpiration().compareTo(Instant.now()) < 0) {
            throw new ExpiredException(format(EXPIRED_TOKEN, uniqueToken.getToken()));
        }

        log.trace("Extracting the user owning this registration token={}", theToken);
        User user = uniqueToken.getUser();

        log.trace("Enabling, updating and adding role to the user {}", user);
        user.setEnabled(true);
        user.addRole(new Role(RoleType.REGISTERED_USER));
        userRepo.save(user);

        log.trace("Deleting the registration token {}", theToken);
        tokenService.deleteByToken(uniqueToken);
        return format("Thank you for verifying %s", user.getUsername());
    }

    public VwLink resendToken(UUID oldToken, String verifyURL) {
        log.trace("UserService - resendToken");

        UniqueToken uniqueToken = tokenService.findByToken(oldToken);
//                .orElseThrow(() -> new NotFoundException(format(NOT_FOUND, oldToken)));

        UUID newToken = UUID.randomUUID();
        log.trace("Updating the registration token {}", oldToken);
        uniqueToken.setToken(newToken);
        uniqueToken.setExpiration(Instant.now());
        tokenService.save(uniqueToken);

        return new VwLink(format(URL_STRING, verifyURL, newToken));
    }

    public String forgotPassword(TPasswordForgot tPasswordForgot, String urlLink) {
        log.trace("UserService - forgotPassword");

        log.trace("Finding user by userName " );
        User user = userRepo.findByUsername(tPasswordForgot.username())
                .orElseThrow(() -> new NotFoundException(format(NOT_FOUND, tPasswordForgot.username())));

        log.trace("Generating the password reset token and publishing the email event");
        UniqueToken uniqueToken = createAndSaveToken(user, TokenType.PASSWORD_RESET);

        publishEmailEventTo(user, urlLink, uniqueToken.getToken(), uniqueToken.getTokenType());
        log.trace("Async - done publishing async event");

        return "Reset link sent to your email";
    }

    public String resetPassword(TPasswordReset tPasswordReset, UUID thePwdResetToken) {
        log.trace("UserService - resetPassword");

        UniqueToken passwordResetToken =  tokenService.findByToken(thePwdResetToken);
//                .orElseThrow(() -> new NotFoundException(format(ENTITY_NOT_EXISTS, thePwdResetToken)));

        if (passwordResetToken.getExpiration().compareTo(Instant.now()) < 0) {
            log.trace("Deleting the password reset token b/c it is expired");
            tokenService.deleteByToken(passwordResetToken);
            throw new ExpiredException(format(EXPIRED_TOKEN, thePwdResetToken));
        }

        log.trace("Encoding, setting and saving the new password");
        User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(tPasswordReset.password()));
        userRepo.save(user);

        log.trace("Deleting the password reset token");
        tokenService.deleteByToken(passwordResetToken);

        return "Password was successfully changed - please login with the new password.";
    }

    public String deleteUser(UUID uuid) {
        log.trace("UserService - deleteUser");
        userRepo.delete(userRepo.findById(uuid)
                .orElseThrow(() -> new UsernameNotFoundException(format(DELETE_FAILURE, uuid))));
        return "Success";
    }


    //========== PRIVATE METHODS =======================//
    //==================================================//
    private UniqueToken createAndSaveToken(User newUser, TokenType registration) {
        log.trace("UserService - createAndSaveToken");
        UUID uuid = UUID.randomUUID();
        UniqueToken token = new UniqueToken(newUser, uuid, registration);

        log.trace("Saving the user + registration token {}", token.getToken());
        token.setUser(newUser);
        userRepo.save(newUser);
        tokenService.save(token);

        log.trace("Done creating and saving {} token", token.getToken());
        return token;
    }

    private User createNewUserOrThrow(TRegisterUser tRegisterUser) {
        log.trace("UserService - createNewUserOrThrow");

        if (!tRegisterUser.password().equals(tRegisterUser.confirmPassword())) {
            throw new ValidationException(format(VALIDATION_FAILURE, "password", tRegisterUser.password(), "must match"));
        }

        Optional<User> user = userRepo.findByUsername(tRegisterUser.userName());
        log.trace("Checking if user is present {}", user);
        if (user.isPresent()) {
            if (!user.get().isEnabled()) {
                throw new NotActiveException(format(NOT_VERIFIED, tRegisterUser.userName()));
            }
            throw new UserExistsException(format(ENTITY_EXISTS, tRegisterUser.userName(), " Login instead."));
        }

        User newUser = toUserFrom(tRegisterUser);
        Role role = roleRepo.findByName(RoleType.PUBLIC_USER);
        log.trace("{}", role);
        newUser.setPassword(passwordEncoder.encode(tRegisterUser.password()));
        newUser.addRole(role);

        return newUser;
    }

    private void publishEmailEventTo(User user, String urlLink, UUID token, TokenType tokenType) {
        log.trace("UserService - publishEmailEvent");
        applicationEventPublisher.publishEvent(new SendEmailEvent(user, urlLink, token, tokenType));
    }

}
