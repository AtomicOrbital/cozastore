package com.example.cozastore.payload.response;

public class SignInResponse {
    private String token;
    private String role;

    public SignInResponse() {
    }

    public SignInResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
