package com.sliit.mtit.AuthService.service;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sliit.mtit.AuthService.dto.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AuthService {

    @Autowired
    RestTemplate restTemplate;

    // Here goes OAuth2 implementation
    public GeneralResponse<AuthResponse> authenticate(LoginRequest loginRequest) {
        if (validateLoginCredentials(loginRequest)) {
            try {
                var entity = restTemplate.postForEntity(
                        "http://localhost:8080/api/v1/users/credentials/",
                        loginRequest,
                        UserResponse.class);

                if (entity == null || entity.getBody() == null)
                    return new GeneralResponse<>(
                            HttpStatus.UNAUTHORIZED.value(),
                            "User does not exists, please try again!",
                            null
                    );

                return new GeneralResponse<>(
                        entity.getStatusCode().value(),
                        "User authenticated successfully!",
                        new AuthResponse(
                                entity.getBody().getId(),
                                entity.getBody().getUsername(),
                                entity.getBody().getEmail(),
                                "1111.1111.1111",
                                "1111.1111.2222",
                                new Date().toString(), // Haven't stored in db
                                1
                        )
                );
            } catch (HttpClientErrorException e) {
                return new GeneralResponse<>(
                        e.getStatusCode().value(),
                        e.getResponseBodyAsString(),
                        null
                );
            } catch (Exception e) {
                return new GeneralResponse<>(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Login failed, please try again!",
                        null
                );
            }
        }

        return new GeneralResponse<>(
                HttpStatus.UNAUTHORIZED.value(),
                "Wrong credentials, please try again!",
                null
        );
    }

    public GeneralResponse signUp(UserRequest signUpRequest) {
        if (validateSignUp(signUpRequest)) {
            try {
                var entity = restTemplate.postForEntity(
                        "http://localhost:8080/api/v1/users/",
                        signUpRequest,
                        GeneralResponse.class);

                if (entity == null || entity.getBody() == null)
                    return new GeneralResponse<>(
                            HttpStatus.BAD_REQUEST.value(),
                            "Registration unsuccessful, please try again!",
                            null
                    );

                return new GeneralResponse<>(
                        entity.getStatusCode().value(),
                        "User sing up successful!",
                        entity.getBody().getBody()
                );
            } catch (HttpClientErrorException e) {
                return new GeneralResponse<>(
                        e.getStatusCode().value(),
                        e.getResponseBodyAsString(),
                        null
                );
            } catch (Exception e) {
                Logger.getLogger("Error").log(Level.SEVERE, e.getMessage());
                return new GeneralResponse<>(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Something went wrong, sign up failed!",
                        null
                );
            }
        }

        return new GeneralResponse<AuthResponse>(
                HttpStatus.UNAUTHORIZED.value(),
                "Email, Username and password cannot be empty!",
                null
        );
    }

    private boolean validateSignUp(UserRequest signUpRequest) {
        return signUpRequest.getEmail() != null && signUpRequest.getEmail() != ""
                && signUpRequest.getEmail() != null && signUpRequest.getEmail() != ""
                && signUpRequest.getPassword() != null && signUpRequest.getPassword() != "";
    }

    private boolean validateLoginCredentials(LoginRequest loginRequest) {
        return loginRequest.getEmail() != null && loginRequest.getEmail() != ""
                && loginRequest.getPassword() != null && loginRequest.getPassword() != "";
    }

    public boolean validateAccessToken(String accessToken) {
        // Dummy validation to prove that validation is here,
        // jsonwebtoken can be use to jwt management
        return (accessToken.contains("Bearer ") && accessToken.length() >= 12
                && accessToken.split("\\.").length == 3);
    }

//    private GeneralResponse<UserResponse> createUserFromUsersMicroService(SignUpRequest signUpRequest) {
//        // To avoid manually mapping to SignUpRequest to LinkedMultiValueMap, I used below method
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, String> map = objectMapper.convertValue(signUpRequest, new TypeReference<>() {});
//
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        map.entrySet().forEach(e -> body.add(e.getKey(), e.getValue()));
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);
//
//        GeneralResponse<UserResponse> generalResponse = restTemplate.exchange(
//                "http://localhost:8080/api/v1/users/",
//                HttpMethod.POST,
//                httpEntity,
//                new ParameterizedTypeReference<GeneralResponse<UserResponse>>() {
//                }).getBody();
//        return generalResponse;
//    }

}
