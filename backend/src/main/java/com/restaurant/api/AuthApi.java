package com.restaurant.api;

import com.restaurant.domain.dto.request.TPasswordForgot;
import com.restaurant.domain.dto.request.TRegisterUser;
import com.restaurant.domain.dto.request.TPasswordReset;
import com.restaurant.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static java.lang.String.format;

@Slf4j
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthApi {

    private static final String URL_FORMAT_STRING = "http://%s:%s%s%s";

    private UserService userService;

    // moved the login route into the spring security filter chain process

    @PostMapping("/register")
    public ResponseEntity<Object> registerNewUser(@RequestBody @Validated TRegisterUser tRegisterUser, HttpServletRequest httpServletRequest) {
        log.trace("Auth Api - registerUser");

        String verifyURL = format(URL_FORMAT_STRING,
                httpServletRequest.getServerName(),
                httpServletRequest.getLocalPort(),
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

        String verifyURL = format(URL_FORMAT_STRING,
                httpServletRequest.getServerName(),
                httpServletRequest.getLocalPort(),
                httpServletRequest.getContextPath(),
                "/auth/verify");

        return ResponseEntity.status(HttpStatus.OK).body(userService.resendToken(oldToken, verifyURL));
    }

    @PostMapping("/password/forgot")
    public ResponseEntity<Object> forgotPassword(HttpServletRequest httpServletRequest,
                                                 @RequestBody TPasswordForgot tPasswordForgot) {
        log.trace("Auth Api - forgotPassword");

        String resetURL = format(URL_FORMAT_STRING,
                httpServletRequest.getServerName(),
                httpServletRequest.getLocalPort(),
                httpServletRequest.getContextPath(),
                "/auth/password/reset");

        return ResponseEntity.ok(userService.forgotPassword(tPasswordForgot, resetURL));
    }

    @PostMapping("/password/reset/{thePwdResetToken}")
    public ResponseEntity<Object> resetPassword(@RequestBody @Validated TPasswordReset tPasswordReset,
                                                @PathVariable("thePwdResetToken") UUID thePwdResetToken) {
        log.trace("Auth Api - resetPassword");

        return ResponseEntity.ok(userService.resetPassword(tPasswordReset, thePwdResetToken));
    }

    // manage and persist token state and revoke token OR leave to front-edn impl??
    @PostMapping("/logout")
    public ResponseEntity<Object> logoutUser() {
        log.trace("Auth Api - logout");
        return ResponseEntity.ok("SUCCESS");
    }

}
