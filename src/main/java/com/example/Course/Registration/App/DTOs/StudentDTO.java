package com.example.Course.Registration.App.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema
public class StudentDTO {

    @NotBlank(message = "Name is mandatory.")
    @Size(min = 3, max = 15, message = "Name must be between 3 and 15 characters.")
    private String name;

    @NotBlank(message = "Email is mandatory.")
    @Email(message = "Email must be valid.")
    private String email;

    @NotBlank(message = "Password is mandatory.")
    @Size(min = 6, max = 2147483647, message = "Password must be at least 6 characters long.")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}