package com.sliit.mtit.AuthService.controller;

import com.sliit.mtit.AuthService.dto.*;
import com.sliit.mtit.AuthService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @GetMapping(produces = "application/json", headers = "accessToken")
    public ResponseEntity<?> authorizeForResource(@RequestHeader String accessToken) {
        // accessToken validation, if expired failed

        // For the time being simple string check
        if (authService.validateAccessToken(accessToken))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(produces = "application/json", consumes = "application/json", path="login")
    public ResponseEntity<GeneralResponse<AuthResponse>> login(@RequestBody LoginRequest loginRequest) {

        GeneralResponse generalResponse = authService.authenticate(loginRequest);

        return new ResponseEntity<GeneralResponse<AuthResponse>>(
            generalResponse,
            HttpStatus.resolve(generalResponse.getStatus())
        );
    }

    @PostMapping(produces = "application/json", consumes = "application/json", path="register")
    public ResponseEntity<GeneralResponse<AuthResponse>> signup(@RequestBody UserRequest signUpRequest) {

        GeneralResponse generalResponse = authService.signUp(signUpRequest);

        return new ResponseEntity<GeneralResponse<AuthResponse>>(
            generalResponse,
            HttpStatus.resolve(generalResponse.getStatus())
        );
    }

    @PostMapping(produces = "application/json", consumes = "application/json",
            headers = "refreshToken")
    public ResponseEntity<?> generateToken(@RequestHeader String refreshToken,
                                           @RequestBody GenerateTokenRequest tokenRequest) {
        // Here goes new token generation by refresh token
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

}
