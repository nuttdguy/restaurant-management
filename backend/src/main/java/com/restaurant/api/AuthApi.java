package com.restaurant.api;

import com.restaurant.domain.dto.request.ForgotPasswordTo;
import com.restaurant.domain.dto.request.LoginTo;
import com.restaurant.domain.dto.request.RegisterUserTo;
import com.restaurant.domain.dto.request.ResetPasswordTo;
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
    public CreateUserResponse registerUser(@RequestBody @Validated RegisterUserTo registerUserTo,
                                           final HttpServletRequest httpServletRequest) {
        log.trace("Auth Api - registerUser");
        log.trace("RegistrationController - registerUserTo");
        return userService.registerUser(registerUserTo, httpServletRequest);
    }

    @GetMapping("/verify/{theToken}")
    public ResponseEntity<Object> verifyRegistration(@PathVariable("theToken") UUID token) {
        log.trace("Auth Api - verifyRegistration");
        log.trace("RegistrationController - verifyRegistration");
        return ResponseEntity.status(HttpStatus.OK).body(userService.verifyRegistration(token));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @Valid LoginTo loginTo) {
        log.trace("Auth Api - loginUser");
        return ResponseEntity.ok(userService.loginUser(loginTo));
    }


//    @PostMapping("/login/password/forgot")
    public ResponseEntity<Object> forgotPassword(@RequestBody ForgotPasswordTo forgotPasswordTo,
                                                 final HttpServletRequest request) {

        log.trace("Auth Api - forgotPassword");
        return ResponseEntity.ok(userService.forgotPassword(forgotPasswordTo, request));

    }

//    @PostMapping("/login/password/reset/{thePwdResetToken}")
    public ResponseEntity<Object> resetPassword(@RequestBody @Validated ResetPasswordTo resetPasswordTo,
                                                @PathVariable("thePwdResetToken") String thePwdResetToken ) {
        log.trace("Auth Api - resetPassword");
        return ResponseEntity.ok(userService.resetPassword(resetPasswordTo, thePwdResetToken));

    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logoutUser() {
        log.trace("Auth Api = logout");
        return ResponseEntity.ok(null);
    }

}
