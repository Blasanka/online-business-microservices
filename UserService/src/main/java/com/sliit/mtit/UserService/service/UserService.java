package com.sliit.mtit.UserService.service;

import com.sliit.mtit.UserService.dto.GeneralResponse;
import com.sliit.mtit.UserService.dto.UserRequest;
import com.sliit.mtit.UserService.dto.UserResponse;
import com.sliit.mtit.UserService.entity.User;
import com.sliit.mtit.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private boolean validateUserRequest(UserRequest request) {
        return request.getEmail() == null || request.getEmail() == ""
                || request.getUsername() == null || request.getUsername() == ""
                || request.getPassword() == null || request.getPassword() == "";
    }

    public GeneralResponse<UserResponse> createUser(UserRequest userRequest) {

        if (validateUserRequest(userRequest)) {
            return new GeneralResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Email, Username and Password required!",
                null
            );
        }

        User newUser = new User(
            userRequest.getUsername(),
            userRequest.getPassword(),
            userRequest.getEmail(),
            userRequest.getDob(),
            userRequest.getNic()
        );

        try {
            User user = userRepository.save(newUser);
            return new GeneralResponse(
                    HttpStatus.CREATED.value(),
                    "Successfully created new user",
                    new UserResponse(user.getId(), user.getEmail(),
                            user.getUsername(), user.getDob(), user.getNic())
            );
        } catch (DataIntegrityViolationException e) {
            // Haven't properly handle json error responses with spring boot capabilities for the time being
            return new GeneralResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null
            );
        } catch (Exception e) {
            return new GeneralResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed! cannot create user",
                    null
            );
        }
    }

    public List<UserResponse> fetchAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .filter(user -> user != null)
                .map(user -> new UserResponse(user.getId(), user.getEmail(),
                        user.getUsername(), user.getDob(), user.getNic()))
                .collect(Collectors.toList());
    }

    public GeneralResponse<UserResponse> fetchUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isEmpty()) {
            return new GeneralResponse(
                HttpStatus.OK.value(),
                null,
                new UserResponse(user.get().getId(), user.get().getEmail(),
                        user.get().getUsername(), user.get().getDob(), user.get().getNic())
            );
        } else {
            return new GeneralResponse(
                    HttpStatus.OK.value(),
                    "User does not found!",
                    null);
        }
    }

    public GeneralResponse updateUser(Long userId, UserRequest userRequest) {
        try {
            Optional<User> user = userRepository.findById(userId);
            if (!user.isEmpty()) {
                userRepository.save(new User(user.get().getId(),
                        userRequest.getUsername(), userRequest.getPassword(),
                        userRequest.getEmail(),
                        userRequest.getDob(), userRequest.getNic()));
                return new GeneralResponse(
                        HttpStatus.CREATED.value(),
                        "Successfully updated user",
                        null);
            } else {
                return new GeneralResponse(
                        HttpStatus.OK.value(),
                        "User does not exists!",
                        null
                );
            }
        } catch (Exception e) {
            Logger.getLogger("info").log(Level.SEVERE, e.getMessage());
            return new GeneralResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed! cannot update user",
                    null
            );
        }
    }

    public GeneralResponse deleteUser(Long userId) {
        try {
            Optional<User> user = userRepository.findById(userId);
            if (!user.isEmpty()) {
                userRepository.deleteById(userId);
                return new GeneralResponse(
                        HttpStatus.NO_CONTENT.value(),
                        "Successfully deleted user",
                        null);
            } else {
                return new GeneralResponse(
                        HttpStatus.OK.value(),
                        "User does not exists!",
                        null
                );
            }
        } catch (Exception e) {
            Logger.getLogger("info").log(Level.SEVERE, e.getMessage());
            return new GeneralResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed! cannot delete user",
                    null
            );
        }
    }

    public GeneralResponse<UserResponse> fetchUserByCredentials(String email, String password) {
        try {
            Optional<User> user = userRepository.findUserByCredentials(email, password);
            if (!user.isEmpty()) {
                return new GeneralResponse(
                    HttpStatus.OK.value(),
                    "User logged in successfully",
                    new UserResponse(user.get().getId(), user.get().getEmail(),
                            user.get().getUsername(), user.get().getDob(), user.get().getNic())
                );
            } else {
                return new GeneralResponse<>(
                        HttpStatus.OK.value(),
                        "User does not exists!",
                        null
                );
            }
        } catch (Exception e) {
            Logger.getLogger("info").log(Level.SEVERE, e.getMessage());
            return new GeneralResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed! cannot delete user",
                    null
            );
        }
    }
}
