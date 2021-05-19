package com.sliit.mtit.UserService.controller;

import com.sliit.mtit.UserService.dto.GeneralResponse;
import com.sliit.mtit.UserService.dto.LoginRequest;
import com.sliit.mtit.UserService.dto.UserResponse;
import com.sliit.mtit.UserService.entity.User;
import com.sliit.mtit.UserService.service.UserService;
import com.sliit.mtit.UserService.dto.UserRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
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

    // Didn't had time to validate with accessToken implemented
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation(
            value = "Add new customer details, usually this request coming from Auth microservice",
            notes = "Add new customer from auth/register to the ABC users database to give making purchase",
            consumes = "application/json",
            produces = "application/json",
            authorizations = @Authorization("accessToken"),
            httpMethod = "POST",
            response = ResponseEntity.class
    )
    public ResponseEntity<GeneralResponse<UserResponse>> createUser(
            @RequestBody UserRequest userRequest) {
        GeneralResponse<UserResponse> response = userService.createUser(userRequest);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    // Didn't had time to validate with accessToken implemented
    @GetMapping(produces = "application/json")
    @ApiOperation(
            value = "Get all customers / users by user id",
            notes = "Retrieves ABC customers. For internal employees / admins",
            produces = "application/json",
            authorizations = @Authorization("accessToken"),
            httpMethod = "GET",
            response = ResponseEntity.class
    )
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserResponse> response = userService.fetchAllUsers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Didn't had time to validate with accessToken implemented
    @GetMapping(produces = "application/json", path = "/{userId}")
    @ApiOperation(
            value = "Get single user / customer by user id",
            notes = "Retrieves ABC customer. For internal employees / admins",
            produces = "application/json",
            httpMethod = "GET",
            response = ResponseEntity.class
    )
    public ResponseEntity<GeneralResponse<UserResponse>> getUser(@ApiParam(name = "userId", required = true) @PathVariable Long userId) {
        GeneralResponse<UserResponse> response = userService.fetchUser(userId);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    // Didnt had time to validate with accessToken implemented
    @PostMapping(consumes = "application/json", produces = "application/json", path="/credentials")
    @ApiOperation(
            value = "Get single user / customer by their email and password",
            notes = "Retrieves ABC customer. Usually auth microservice call this to check user exists to login access to make purchases of ABC customers",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "POST",
            response = ResponseEntity.class
    )
    public ResponseEntity<UserResponse> getUserByCredentials(@RequestBody LoginRequest loginRequest) {
        GeneralResponse<UserResponse> response = userService.fetchUserByCredentials(
                loginRequest.getEmail(), loginRequest.getPassword());
        return new ResponseEntity<>(response.getBody(), HttpStatus.resolve(response.getStatus()));
    }

    // Didn't had time to validate with accessToken implemented
    @PutMapping(produces = "application/json", path="/{userId}")
    @ApiOperation(
            value = "Update customer details by user id",
            notes = "Protected request. To update user details of registered customer",
            consumes = "application/json",
            produces = "application/json",
            httpMethod = "PUT",
            response = ResponseEntity.class
    )
    public ResponseEntity<GeneralResponse<?>> updateUser(
            @ApiParam(name = "userId", required = true) @PathVariable Long userId, @RequestBody UserRequest userRequest) {
        GeneralResponse<User> response = userService.updateUser(userId, userRequest);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    // Didn't had time to validate with accessToken implemented
    @DeleteMapping(produces = "application/json", path="/{userId}")
    @ApiOperation(
            value = "Delete customer by user id",
            notes = "Protected request. To delete registered customer",
            produces = "application/json",
            httpMethod = "DELETE",
            response = ResponseEntity.class
    )
    public ResponseEntity<GeneralResponse<?>> deleteUser(@ApiParam(name = "userId", required = true) @PathVariable Long userId) {
        GeneralResponse<User> response = userService.deleteUser(userId);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }
}
