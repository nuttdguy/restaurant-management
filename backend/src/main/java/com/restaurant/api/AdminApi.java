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
@RequestMapping("/p/admin/")
public class AdminApi {

    private UserService userService;

    @GetMapping(value = {"/user/username/{username}"})
    public ResponseEntity<Object> findUserByUsername(@PathVariable("username") String username) {
        log.trace("UserController - findUserByUsername={}", username);

        return ResponseEntity.status(HttpStatus.OK).body(userService.findByUsername(username));
    }

    @DeleteMapping("/user/username/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(username));
    }

    @DeleteMapping("/user/id/{uuid}")
    public ResponseEntity<Object> deleteUser(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(uuid));
    }
}