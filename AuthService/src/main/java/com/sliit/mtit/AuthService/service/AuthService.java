package com.sliit.mtit.AuthService.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sliit.mtit.AuthService.dto.*;
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

                System.out.println(entity.getBody());

                if (entity == null || entity.getBody() == null)
                    return new GeneralResponse<>(
                            HttpStatus.UNAUTHORIZED.value(),
                            "User does not exists, please try again!",
                            null
                    );

                return new GeneralResponse<>(
                        HttpStatus.UNAUTHORIZED.value(),
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
                        HttpStatus.UNAUTHORIZED.value(),
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

    public GeneralResponse signUp(SignUpRequest signUpRequest) {
        if (validateSignUp(signUpRequest)) {

            try {
                // To avoid manually mapping to SignUpRequest to LinkedMultiValueMap, I used below method
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> map = objectMapper.convertValue(signUpRequest, new TypeReference<>() {});

                MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
                map.entrySet().forEach(e -> body.add(e.getKey(), e.getValue()));

                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);

                GeneralResponse<UserResponse> generalResponse = restTemplate.exchange(
                        "http://localhost:8080/api/v1/users/",
                        HttpMethod.POST,
                        httpEntity,
                        new ParameterizedTypeReference<GeneralResponse<UserResponse>>() {
                        }).getBody();

                return new GeneralResponse<>(
                        HttpStatus.BAD_REQUEST.value(),
                        "User sing up successful!",
                        generalResponse.getBody()
                );
            } catch (Exception e) {
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

    private boolean validateSignUp(SignUpRequest signUpRequest) {
        return signUpRequest.getEmail() != null && signUpRequest.getEmail() != ""
                && signUpRequest.getEmail() != null && signUpRequest.getEmail() != ""
                && signUpRequest.getPassword() != null && signUpRequest.getPassword() != "";
    }

    private boolean validateLoginCredentials(LoginRequest loginRequest) {
        return loginRequest.getEmail() != null && loginRequest.getEmail() != ""
                && loginRequest.getPassword() != null && loginRequest.getPassword() != "";
    }

}
