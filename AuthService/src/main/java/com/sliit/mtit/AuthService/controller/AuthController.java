package com.sliit.mtit.AuthService.controller;

import com.sliit.mtit.AuthService.dto.GeneralResponse;
import com.sliit.mtit.AuthService.dto.LoginRequest;
import com.sliit.mtit.AuthService.dto.AuthResponse;
import com.sliit.mtit.AuthService.dto.SignUpRequest;
import com.sliit.mtit.AuthService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping(produces = "application/json", consumes = "application/json", path="login")
    public ResponseEntity<GeneralResponse<AuthResponse>> login(@RequestBody LoginRequest loginRequest) {

        GeneralResponse generalResponse = authService.authenticate(loginRequest);

        return new ResponseEntity<GeneralResponse<AuthResponse>>(
            generalResponse,
            HttpStatus.resolve(generalResponse.getStatus())
        );
    }

    @PostMapping(produces = "application/json", consumes = "application/json", path="register")
    public ResponseEntity<GeneralResponse<AuthResponse>> signup(@RequestBody SignUpRequest signUpRequest) {

        GeneralResponse generalResponse = authService.signUp(signUpRequest);

        return new ResponseEntity<GeneralResponse<AuthResponse>>(
                generalResponse,
                HttpStatus.resolve(generalResponse.getStatus())
        );
    }

}
