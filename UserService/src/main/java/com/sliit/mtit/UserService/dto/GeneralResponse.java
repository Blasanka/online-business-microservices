package com.sliit.mtit.UserService.dto;

public class GeneralResponse<T> {

    private Integer status;
    private String message;
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
