package com.restaurant.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.restaurant.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user/")
public class UserApi {

    private UserService userService;

    @GetMapping(value = {"/userName/{userName}"})
    public ResponseEntity<Object> findUserByUsername(@PathVariable("userName") String username) {
        log.trace("UserApi- findUserByUsername={}", username);

        return ResponseEntity.status(HttpStatus.OK).body(userService.loadUserByUsername(username));
    }

    @GetMapping(value = "/id/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable("userId") UUID id) {
        log.trace("UserApi - getUserById");

        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Object> deleteUserById(@PathVariable("userId") UUID id) {
        log.trace("UserApi - deleteUserById");

        return ResponseEntity.ok(userService.deleteUser(id));
    }


}