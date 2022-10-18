package com.restaurant.api;

import com.restaurant.domain.dto.request.ForgotPasswordRequest;
import com.restaurant.domain.dto.request.LoginRequest;
import com.restaurant.domain.dto.request.RegisterRequest;
import com.restaurant.domain.dto.request.ResetPasswordRequest;
import com.restaurant.domain.dto.response.CreateUserResponse;
import com.restaurant.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthApi {

    private UserService userService;

    @PostMapping("/register")
    public CreateUserResponse registerUser(@RequestBody @Validated RegisterRequest registerRequest,
                                           final HttpServletRequest httpServletRequest) {

        log.trace("RegistrationController - registerUser");
        return userService.registerUser(registerRequest, httpServletRequest);
    }

    @GetMapping("/verify/{theToken}")
    public ResponseEntity<Object> verifyRegistration(@PathVariable("theToken") UUID token) {

        log.trace("RegistrationController - verifyRegistration");
        return ResponseEntity.status(HttpStatus.OK).body(userService.verifyRegistration(token));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        log.trace("Login controller - loginUser");
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }


    @PostMapping("/login/password/forgot")
    public ResponseEntity<Object> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest,
                                                 final HttpServletRequest request) {

        log.trace("Login controller - forgotPassword");
        return ResponseEntity.ok(userService.forgotPassword(forgotPasswordRequest, request));

    }

    @PostMapping("/login/password/reset/{thePwdResetToken}")
    public ResponseEntity<Object> resetPassword(@RequestBody @Validated ResetPasswordRequest resetPasswordRequest,
                                                @PathVariable("thePwdResetToken") String thePwdResetToken ) {
        log.trace("Login controller - changePassword");
        return ResponseEntity.ok(userService.resetPassword(resetPasswordRequest, thePwdResetToken));

    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logoutUser() {
        log.trace("Auth Api = logout");
        return ResponseEntity.ok(null);
    }

}
