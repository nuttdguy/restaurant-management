package com.restaurant.service;

import com.restaurant.domain.dto.request.TForgotPassword;
import com.restaurant.domain.dto.request.TLogin;
import com.restaurant.domain.dto.request.TRegisterUser;
import com.restaurant.domain.dto.request.TResetPassword;
import com.restaurant.domain.dto.response.VwUser;
import com.restaurant.domain.dto.response.VwJwt;
import com.restaurant.domain.model.RegistrationToken;
import com.restaurant.domain.model.Role;
import com.restaurant.event.RegistrationEvent;
import com.restaurant.exception.*;
import com.restaurant.jwt.JwtUtil;
import com.restaurant.repository.IRoleRepo;
import com.restaurant.repository.IUserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.restaurant.domain.model.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
@AllArgsConstructor
@Transactional
public class UserService {

    private final JwtUtil jwtUtil;
    private final TokenService tokenService;
    private final IRoleRepo roleRepo;
    private final IUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    public User findByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(format(NOT_FOUND, username)));
    }

    public User findById(UUID id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(format(NOT_FOUND, id)));
    }

    public VwUser registerNewUser(TRegisterUser tRegisterUser, String verifyURL) {
        log.trace("UserService - TRegisterUser");

        if (!tRegisterUser.password().equals(tRegisterUser.confirmPassword())) {
            throw new ValidationException(format(VALIDATION_FAILURE, "password", tRegisterUser.password(), "must match"));
        }

        User newUser = createNewUserOrThrowIfFound(tRegisterUser);
        newUser.setPassword(passwordEncoder.encode(tRegisterUser.password()));

        log.trace("Generating the register token and publishing the register email event");
        UUID token = UUID.randomUUID();
        RegistrationToken registrationToken = new RegistrationToken(newUser, token);
        publishEmailEvent(newUser, verifyURL, token);

        log.trace("Saving the user + registration token {}", registrationToken.getUuid());
        registrationToken.setUser(newUser);
        userRepo.save(newUser);
        tokenService.save(registrationToken);

        return new VwUser(newUser.getUuid());
    }

    private User createNewUserOrThrowIfFound(TRegisterUser tRegisterUser) {
        log.trace("UserService - extractUserThrowIfFound");

        Optional<User> user = userRepo.findByUsername(tRegisterUser.username());
        log.trace("Checking if user is present {}", user);
        if (user.isPresent()) {
            if (!user.get().isEnabled()) {
                throw new NotActiveException(format(NOT_VERIFIED, tRegisterUser.username()));
            }
            throw new UserExistsException(format(ENTITY_EXISTS, tRegisterUser.username(), " Login instead."));
        }
        return toUserFrom(tRegisterUser);
    }

    private void publishEmailEvent(User user, String verifyURL, UUID token) {
        log.trace("UserService - publishEmailEvent");
        applicationEventPublisher.publishEvent(new RegistrationEvent(user, verifyURL, token));
    }

    public String verifyRegistration(UUID theToken) {
        log.trace("UserService - verifyRegistrationToken");

        RegistrationToken registrationToken = tokenService.findRegisterTokenById(theToken)
                .orElseThrow(() -> new NotFoundException(format(NOT_FOUND, theToken)));

        log.trace("Checking if the token is expired");
        if (registrationToken.getExpiration().compareTo(Instant.now()) < 0) {
            tokenService.deleteRegisterToken(registrationToken);
            throw new ExpiredException(format(EXPIRED_TOKEN, registrationToken));
        }

        log.trace("Extracting the user owning this registration token={}", theToken);
        User user = registrationToken.getUser();

        log.trace("Enabling and updating the user {}", user);
        user.setEnabled(true);
        List<Role> roles = roleRepo.findAll();
        user.getAuthorities().add(roles.get(0));
        user.getAuthorities().add(roles.get(1));
        userRepo.save(user);

        log.trace("Deleting the registration token {}", theToken);
        tokenService.deleteRegisterToken(registrationToken);
        return format("Thank you for verifying %s", user.getUsername());
    }

    public VwJwt loginUser(TLogin tLogin) throws BadCredentialsException {
        log.trace("UserService - loginUser");

        User user = userRepo.findByUsername(tLogin.username())
                .orElseThrow(() -> new NotFoundException(format(NOT_FOUND, tLogin.username())));
        
        log.trace("Validating the account is activated and has a valid password");
        if (!passwordEncoder.matches(tLogin.password(), user.getPassword())) {
            throw new BadCredentialsException(format(BAD_CREDENTIAL, "username", "password"));
        }
        if (!user.isEnabled()) {
            throw new NotActiveException(format(NOT_VERIFIED, tLogin.username()));
        }

        // todo - add roles into jwt authorization flow
        // todo - enable after understanding how jwt and refresh token works in auuth flow
        log.trace("generated the jwt token with user details");
        log.trace("extract the granted authorities");
//        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

//        RefreshToken refreshToken = refreshTokenService.createRefreshToken(UUID.randomUUID());
        String jwt = jwtUtil.generateJwtToken(user);
        String refreshToken1 = UUID.randomUUID().toString();
        return new VwJwt(format("Bearer %s", jwt), refreshToken1, user.getUsername(), user.getUuid());
    }

    public Object forgotPassword(TForgotPassword tForgotPassword, HttpServletRequest request) {
        return null;
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

}
