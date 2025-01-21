package com.example.Course.Registration.App.Util;


public class JwtRequest {
    private String email;
    private String password;

    // Default constructor
    public JwtRequest() {
    }

    // Parameterized constructor
    public JwtRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
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
}
