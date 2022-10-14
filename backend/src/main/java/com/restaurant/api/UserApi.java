package com.restaurant.api;

import com.restaurant.domain.dto.response.UserVerifiedResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.restaurant.domain.dto.request.RegisterRequest;
import com.restaurant.domain.dto.response.CreateUserResponse;
import com.restaurant.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user/")
public class UserApi {

    private UserService userService;

    @PostMapping("register")
    public CreateUserResponse registerUser(@RequestBody @Validated RegisterRequest registerRequest,
                                           final HttpServletRequest httpServletRequest) {

        log.trace("RegistrationController - registerUser");
        return userService.registerUser(registerRequest, httpServletRequest);
    }

    @GetMapping(value = {"/username/{username}"})
    public ResponseEntity<Object> findUserByUsername(@PathVariable("username") String username) {
        log.trace("UserController - findUserByUsername={}", username);

        return ResponseEntity.status(HttpStatus.OK).body(userService.findByUsername(username));
    }

    @GetMapping(value = "/verify/{token}")
    public ResponseEntity<Object> verifyRegistration(@PathVariable("token") String token) {

        log.trace("RegistrationController - verifyRegistration");
        return ResponseEntity.status(HttpStatus.OK).body(userService.verifyRegistration(token));
    }


}