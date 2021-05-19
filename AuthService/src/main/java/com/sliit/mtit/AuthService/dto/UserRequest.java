package com.sliit.mtit.AuthService.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "This is the request body of user microservice communication")
public class UserRequest {

    private String email;
    private String password;
    private String username;
    private String dob;
    private String nic;

    public UserRequest() {
    }

    public UserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserRequest(String email, String password, String username, String dob, String nic) {
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "SignUpRequest{" +
                "email:'" + email + '\'' +
                ", username:'" + username + '\'' +
                ", password:'" + password + '\'' +
                ", dob:'" + dob + '\'' +
                ", nic:'" + nic + '\'' +
                '}';
    }
}
