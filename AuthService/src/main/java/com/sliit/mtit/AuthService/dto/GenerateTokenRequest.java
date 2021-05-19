package com.sliit.mtit.AuthService.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "To generate new token and to get generated token")
public class GenerateTokenRequest {

    @ApiModelProperty(notes = "Registered or logged in user id")
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
