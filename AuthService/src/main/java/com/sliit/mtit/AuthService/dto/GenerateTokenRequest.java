package com.sliit.mtit.AuthService.dto;

public class GenerateTokenRequest {

    private Long userId;

    public GenerateTokenRequest() {
    }

    public GenerateTokenRequest(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
