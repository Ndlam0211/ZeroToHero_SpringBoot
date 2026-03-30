package com.lamnd.zerotohero.controller;

import com.lamnd.zerotohero.dto.request.UserCreationRequest;
import com.lamnd.zerotohero.dto.request.UserUpdationRequest;
import com.lamnd.zerotohero.entity.User;
import com.lamnd.zerotohero.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    User createUser(@RequestBody @Valid UserCreationRequest request) {
        return userService.createUser(request);
    }

    @GetMapping
    List<User> getAllUsers() {
        return userService.getAllUser();
    }

    @GetMapping("/{userId}")
    ResponseEntity<?> getUserById(@PathVariable("userId") String userId) {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    ResponseEntity<?> updateUserById(@PathVariable("userId") String userId, @RequestBody UserUpdationRequest request) {
        try {
            User user = userService.updateUserById(userId,request);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("User Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{userId}")
    ResponseEntity<?> deleteUserById(@PathVariable("userId") String userId) {
        userService.deleteUserById(userId);
        return new ResponseEntity<>("User Deleted Successfully", HttpStatus.NO_CONTENT);
    }
}
