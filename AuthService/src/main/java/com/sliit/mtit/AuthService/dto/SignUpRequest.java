package com.sliit.mtit.AuthService.dto;

public class SignUpRequest {

    private String email;
    private String username;
    private String password;
    private String dob;
    private String nic;

    public SignUpRequest() {
    }

    public SignUpRequest(String email, String username, String password, String dob, String nic) {
        this.email = email;
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
