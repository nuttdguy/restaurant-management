package com.restaurant.service;

import com.restaurant.domain.dto.request.ForgotPasswordRequest;
import com.restaurant.domain.dto.request.LoginRequest;
import com.restaurant.domain.dto.request.RegisterRequest;
import com.restaurant.domain.dto.request.ResetPasswordRequest;
import com.restaurant.domain.dto.response.CreateUserResponse;
import com.restaurant.domain.dto.response.JwtResponse;
import com.restaurant.domain.dto.response.UserVerifiedResponse;
import com.restaurant.domain.mapper.UserMapper;
import com.restaurant.domain.model.RegistrationToken;
import com.restaurant.domain.model.Role;
import com.restaurant.domain.model.RoleType;
import com.restaurant.event.RegistrationEvent;
import com.restaurant.exception.TokenExpiredException;
import com.restaurant.exception.UserNotFoundException;
import com.restaurant.jwt.JwtUtil;
import com.restaurant.repository.IUserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.restaurant.domain.model.User;
import com.restaurant.exception.AccountNotActiveException;
import com.restaurant.exception.UserExistsException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
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
import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final String REGISTER_URL_SUFFIX = "/auth/verify/";
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;
    private final IUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;


    public User findByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(format("%s not found", username)));
    }

    @Transactional
    public CreateUserResponse registerUser(RegisterRequest registerRequest,
                                           HttpServletRequest httpServletRequest) {
        log.trace("UserService - registerUser");
        log.trace("Create a new user from request or throw if user is found");
        hasMatchingPassword(registerRequest);
        User newUser = extractUserThrowIfFound(registerRequest);

        log.trace("Process new registration");
        log.trace("Encode and set the password");
        newUser.setPassword(passwordEncoder.encode(registerRequest.password()));

        log.trace("Publish email event");
        RegistrationToken registrationToken = publishEmailEvent(httpServletRequest, newUser);

        // wrap up and save
        log.trace("Registering the user");
        log.trace("Returning the user {}", newUser);

        // save the user and registration token
        log.trace("Saving the user + registration token {}", registrationToken.getUuid());
        userRepo.save(newUser);
        tokenService.save(registrationToken);
        return toCreateUserFrom(registrationToken.getUser());

    }

    @Transactional
    public UserVerifiedResponse verifyRegistration(UUID theToken) {
        log.trace("RegistrationService - verifyRegistrationToken");

        RegistrationToken registrationToken = tokenService.findRegisterTokenById(theToken);

        log.trace("Checking if the token is expired");
        verifyExpiration(registrationToken);

        log.trace("Extracting the user owning this registration token={}", theToken);
        User user = registrationToken.getUser();
        log.trace("Extracting the user from registration token= {}", user);

        log.trace("Enabling and updating the user {}", user);
        user.setEnabled(true);
        user.getAuthorities().add(new Role(RoleType.REGISTERED_USER));
        userRepo.save(user);

        log.trace("Deleting the registration token {}", theToken);
        tokenService.deleteRegisterToken(registrationToken);
        return toUserVerifiedResponseFrom(user);
    }

    @Transactional
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


    private User extractUserThrowIfFound(RegisterRequest registerRequest) {
        Optional<User> user = userRepo.findByUsername(registerRequest.username());
        log.trace("User ............. {}", user);
        if (user.isPresent()) {
            if (!user.get().isEnabled()) {
                log.trace("User found, but has not been activated / enabled");
                throw new AccountNotActiveException("An account was found for " + registerRequest.username() +
                        " -> Please verify your email and activate your account.");
            }
            log.trace("User exists and is active {}", registerRequest.username());
            throw new UserExistsException("User "+ registerRequest.username()+" already exists. Please login instead.");
        }
        return toUserFrom(registerRequest);
    }

    private RegistrationToken publishEmailEvent(HttpServletRequest httpServletRequest, User user) {
        log.trace("Building and then email registration url");
        String url = constructRegisterUrl(httpServletRequest) + REGISTER_URL_SUFFIX ;

        // create verification token for user with link
        UUID token = UUID.randomUUID();
        String tokenUrl = url + token.toString();
        RegistrationToken registrationToken = new RegistrationToken(user, token);

        log.trace("Publishing the registration event");
        // publish a new user registration event to
        applicationEventPublisher.publishEvent(new RegistrationEvent(user, tokenUrl));
        return registrationToken;
    }

    private void hasMatchingPassword(RegisterRequest registerRequest) {
        if (!registerRequest.password().equals(registerRequest.confirmPassword())) {
            throw new ValidationException("Passwords do not match");
        }
    }

    private String constructRegisterUrl(HttpServletRequest request) {
        return "http://"+
                request.getServerName()+
                ":"+
                request.getServerPort()+
                request.getContextPath();
    }

    public JwtResponse loginUser(LoginRequest loginRequest) {
        log.trace("LoginService - loginUser");

        log.trace("Fetching the user");
        User user = userRepo.findByUsername(loginRequest.username())
                .orElseThrow(() ->
                        new UsernameNotFoundException(format("%s not found", loginRequest.username())));

        log.trace("passwordEncoder.matches");
        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new BadCredentialsException("Password is not valid");
        }

        log.trace("extract the granted authorities");
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        String jwt = jwtUtil.generateJwtToken(user);
        log.trace("generate jwt token with user details {}", jwt);

        // todo - enable when understanding how this works
        log.trace("create a refresh token ");
//        RefreshToken refreshToken = refreshTokenService.createRefreshToken(UUID.randomUUID());

        log.trace("Sending jwt response");
        return new JwtResponse("Bearer ",
                jwt,
                UUID.randomUUID().toString(),
                user.getUsername(),
                roles);
    }


    public Object forgotPassword(ForgotPasswordRequest forgotPasswordRequest, HttpServletRequest request) {
        return null;
    }

    public Object resetPassword(ResetPasswordRequest resetPasswordRequest, String thePwdResetToken) {
        return null;
    }
}
