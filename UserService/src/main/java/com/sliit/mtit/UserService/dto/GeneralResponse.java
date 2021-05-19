package com.sliit.mtit.UserService.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Common response for all requests that includes status code and message")
public class GeneralResponse<T> {

    @ApiModelProperty(notes = "Status code of response")
    private Integer status;
    @ApiModelProperty(notes = "Message that described error, or success")
    private String message;
    @ApiModelProperty(notes = "Particular url segment response body")
    private T body;

    public GeneralResponse(Integer status, String message, T body) {
        this.status = status;
        this.message = message;
        this.body = body;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
