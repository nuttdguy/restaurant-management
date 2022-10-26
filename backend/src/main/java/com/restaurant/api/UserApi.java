package com.restaurant.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.restaurant.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user/")
public class UserApi {

    private UserService userService;

    @GetMapping(value = {"/username/{username}"})
    public ResponseEntity<Object> findUserByUsername(@PathVariable("username") String username) {
        log.trace("UserApi- findUserByUsername={}", username);

        return ResponseEntity.status(HttpStatus.OK).body(userService.findByUsername(username));
    }


}