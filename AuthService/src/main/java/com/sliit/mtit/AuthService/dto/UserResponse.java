package com.sliit.mtit.AuthService.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "This is the response of user microservice communication returns")
public class UserResponse {

    @ApiModelProperty(notes = "Registered user id")
    private Long id;
    private String email;
    private String username;
    private String dob;
    private String nic;

    public UserResponse() {
    }

    public UserResponse(Long id, String email, String username, String dob, String nic) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.dob = dob;
        this.nic = nic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id:" + id +
                ", email:'" + email + '\'' +
                ", username:'" + username + '\'' +
                ", dob:'" + dob + '\'' +
                ", nic:'" + nic + '\'' +
                '}';
    }
}
