package com.restaurant.service;

import com.restaurant.domain.dto.request.ForgotPasswordTo;
import com.restaurant.domain.dto.request.LoginTo;
import com.restaurant.domain.dto.request.RegisterUserTo;
import com.restaurant.domain.dto.request.ResetPasswordTo;
import com.restaurant.domain.dto.response.CreateUserResponse;
import com.restaurant.domain.dto.response.JwtResponse;
import com.restaurant.domain.dto.response.UserVerifiedResponse;
import com.restaurant.domain.model.RegistrationToken;
import com.restaurant.domain.model.Role;
import com.restaurant.domain.model.RoleType;
import com.restaurant.event.RegistrationEvent;
import com.restaurant.exception.TokenExpiredException;
import com.restaurant.jwt.JwtUtil;
import com.restaurant.repository.IRoleRepo;
import com.restaurant.repository.IUserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.restaurant.domain.model.User;
import com.restaurant.exception.AccountNotActiveException;
import com.restaurant.exception.UserExistsException;
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
                .orElseThrow(() -> new UsernameNotFoundException(format("%s not found", username)));
    }

    public CreateUserResponse registerUser(RegisterUserTo registerUserTo,
                                           HttpServletRequest httpServletRequest) {
        log.trace("UserService - registerUserTo");
        log.trace("Create a new user from request or throw if user is found");
        hasMatchingPassword(registerUserTo);
        User newUser = extractUserThrowIfFound(registerUserTo);

        log.trace("Process new registration");
        log.trace("Encode and set the password");
        newUser.setPassword(passwordEncoder.encode(registerUserTo.password()));

        log.trace("Publish email event");
        RegistrationToken registrationToken = publishEmailEvent(httpServletRequest, newUser);

        // wrap up and save
        log.trace("Registering the user");
        log.trace("Returning the user {}", newUser);

        // save the user and registration token
        log.trace("Saving the user + registration token {}", registrationToken.getUuid());
        registrationToken.setUser(newUser);

        userRepo.save(newUser);
        tokenService.save(registrationToken);
        return toCreateUserFrom(registrationToken.getUser());

    }

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
        List<Role> roles = roleRepo.findAll();
        user.getAuthorities().add(roles.get(0));
        user.getAuthorities().add(roles.get(1));
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

    public int deleteUser(String username) {
        userRepo.delete(userRepo
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(format("Delete failed because user %s was not found", username))));
        return 1;
    }

    public int deleteUser(UUID uuid) {
        userRepo.delete(userRepo
                .findById(uuid)
                .orElseThrow(() -> new UsernameNotFoundException(format("Delete failed because user of id %s was not found", uuid))));
        return 1;
    }

    private User extractUserThrowIfFound(RegisterUserTo registerUserTo) {
        Optional<User> user = userRepo.findByUsername(registerUserTo.username());
        log.trace("User ............. {}", user);
        if (user.isPresent()) {
            if (!user.get().isEnabled()) {
                log.trace("User found, but has not been activated / enabled");
                throw new AccountNotActiveException("An account was found for " + registerUserTo.username() +
                        ". Please verify your email and activate your account.");
            }
            log.trace("User exists and is active {}", registerUserTo.username());
            throw new UserExistsException("User "+ registerUserTo.username()+" already exists. Please login instead.");
        }
        return toUserFrom(registerUserTo);
    }

    private RegistrationToken publishEmailEvent(HttpServletRequest httpServletRequest, User user) {
        log.trace("Building and then email registration url");
        String REGISTER_URL_SUFFIX = "/auth/verify/";
        String url = constructRegisterUrl(httpServletRequest) + REGISTER_URL_SUFFIX;

        // create verification token for user with link
        UUID token = UUID.randomUUID();
        String tokenUrl = url + token;
        RegistrationToken registrationToken = new RegistrationToken(user, token);

        log.trace("Publishing the registration event");
        // publish a new user registration event to
        applicationEventPublisher.publishEvent(new RegistrationEvent(user, tokenUrl));
        return registrationToken;
    }

    private void hasMatchingPassword(RegisterUserTo registerUserTo) {
        if (!registerUserTo.password().equals(registerUserTo.confirmPassword())) {
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

    public JwtResponse loginUser(LoginTo loginTo) throws BadCredentialsException {
        log.trace("LoginService - loginUser");

        log.trace("Fetching the user");
        User user = userRepo.findByUsername(loginTo.username())
                .orElseThrow(() ->
                        new UsernameNotFoundException(format("%s not found", loginTo.username())));

        log.trace("passwordEncoder.matches");
        if (!passwordEncoder.matches(loginTo.password(), user.getPassword())) {
            throw new BadCredentialsException("Password is not valid");
        }

        if (!user.isEnabled()) {
            throw new AccountNotActiveException("This account has not been verified, please confirm your email.");
        }

        // todo - add roles into jwt authorization flow
        log.trace("extract the granted authorities");
//        List<String> roles = user.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority).toList();

        String jwt = jwtUtil.generateJwtToken(user);
        log.trace("generate jwt token with user details {}", jwt);

        // todo - enable after understanding how this works
        log.trace("create a refresh token ");
//        RefreshToken refreshToken = refreshTokenService.createRefreshToken(UUID.randomUUID());

        log.trace("Sending jwt response");
        return new JwtResponse("Bearer ",
                jwt,
                UUID.randomUUID().toString(),
                user.getUsername(),
                user.getUuid());
    }


    public Object forgotPassword(ForgotPasswordTo forgotPasswordTo, HttpServletRequest request) {
        return null;
    }

    public Object resetPassword(ResetPasswordTo resetPasswordTo, String thePwdResetToken) {
        return null;
    }
}
