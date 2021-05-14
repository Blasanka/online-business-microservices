package com.sliit.mtit.UserService.controller;

import com.sliit.mtit.UserService.dto.GeneralResponse;
import com.sliit.mtit.UserService.dto.LoginRequest;
import com.sliit.mtit.UserService.dto.UserResponse;
import com.sliit.mtit.UserService.entity.User;
import com.sliit.mtit.UserService.service.UserService;
import com.sliit.mtit.UserService.dto.UserRequest;
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

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<GeneralResponse<UserResponse>> createUser(
            @RequestBody UserRequest userRequest) {
        GeneralResponse<UserResponse> response = userService.createUser(userRequest);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserResponse> response = userService.fetchAllUsers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", path = "/{userId}")
    public ResponseEntity<GeneralResponse<UserResponse>> getUser(@PathVariable Long userId) {
        GeneralResponse<UserResponse> response = userService.fetchUser(userId);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    @PostMapping(consumes = "application/json", produces = "application/json", path="/credentials")
    public ResponseEntity<UserResponse> getUserByCredentials(@RequestBody LoginRequest loginRequest) {
        GeneralResponse<UserResponse> response = userService.fetchUserByCredentials(
                loginRequest.getEmail(), loginRequest.getPassword());
        return new ResponseEntity<>(response.getBody(), HttpStatus.resolve(response.getStatus()));
    }

    @PutMapping(produces = "application/json", path="/{userId}")
    public ResponseEntity<GeneralResponse<?>> updateUser(
            @PathVariable Long userId, @RequestBody UserRequest userRequest) {
        GeneralResponse<User> response = userService.updateUser(userId, userRequest);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    @DeleteMapping(produces = "application/json", path="/{userId}")
    public ResponseEntity<GeneralResponse<?>> deleteUser(@PathVariable Long userId) {
        GeneralResponse<User> response = userService.deleteUser(userId);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }
}
