package com.restaurant.api;

import com.restaurant.domain.dto.request.TForgotPassword;
import com.restaurant.domain.dto.request.TLogin;
import com.restaurant.domain.dto.request.TRegisterUser;
import com.restaurant.domain.dto.request.TResetPassword;
import com.restaurant.domain.dto.response.VwUser;
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

import static java.lang.String.format;

@Slf4j
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthApi {

    private UserService userService;

    // moved the login route into the spring security filter chain process

    @PostMapping("/register")
    public ResponseEntity<Object> registerNewUser(@RequestBody @Validated TRegisterUser tRegisterUser, HttpServletRequest httpServletRequest) {
        log.trace("Auth Api - registerUser");

        String verifyURL = format("http://%s:%s%s%s",
                httpServletRequest.getServerName(),
                httpServletRequest.getServerPort(),
                httpServletRequest.getContextPath(),
                "/auth/verify");

        return ResponseEntity.ok(userService.registerNewUser(tRegisterUser, verifyURL));
    }

    @GetMapping("/verify/{verifyToken}")
    public ResponseEntity<Object> verifyRegistration(@PathVariable("verifyToken") UUID token) {
        log.trace("Auth Api - verifyRegistration");
        return ResponseEntity.status(HttpStatus.OK).body(userService.verifyUserRegistration(token));
    }

    @GetMapping("/verify/resend/{oldVerifyToken}")
    public ResponseEntity<Object> resendVerifyToken(@PathVariable("oldVerifyToken") UUID oldToken, HttpServletRequest httpServletRequest) {
        log.trace("Auth Api - resendVerifyToken");

        String verifyURL = format("http://%s:%s%s%s",
                httpServletRequest.getServerName(),
                httpServletRequest.getServerPort(),
                httpServletRequest.getContextPath(),
                "/auth/verify");

        return ResponseEntity.status(HttpStatus.OK).body(userService.resendRegistrationToken(oldToken, verifyURL));
    }


//    @PostMapping("/login/password/forgot")
    public ResponseEntity<Object> forgotPassword(@RequestBody TForgotPassword tForgotPassword,
                                                 final HttpServletRequest request) {

        log.trace("Auth Api - forgotPassword");
        return ResponseEntity.ok(userService.forgotPassword(tForgotPassword, request));

    }

//    @PostMapping("/login/password/reset/{thePwdResetToken}")
    public ResponseEntity<Object> resetPassword(@RequestBody @Validated TResetPassword tResetPassword,
                                                @PathVariable("thePwdResetToken") String thePwdResetToken ) {
        log.trace("Auth Api - resetPassword");
        return ResponseEntity.ok(userService.resetPassword(tResetPassword, thePwdResetToken));

    }

    // todo, revoke token
    @PostMapping("/logout")
    public ResponseEntity<Object> logoutUser() {
        log.trace("Auth Api - logout");
        return ResponseEntity.ok("SUCCESS");
    }

}
