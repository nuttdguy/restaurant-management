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

    @GetMapping(value = {"/username/{username}"})
    public ResponseEntity<Object> findUserByUsername(@PathVariable("username") String username) {
        log.trace("UserController - findUserByUsername={}", username);

        return ResponseEntity.status(HttpStatus.OK).body(userService.findByUsername(username));
    }


}