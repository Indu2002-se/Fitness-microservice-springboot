package com.example.userservice.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.boot.convert.DataSizeUnit;

@Data
public class RegisterRequest {

    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password is Required")
    @Size(min = 6, message = "password must have atleast 6 characters")
    private String password;

    private String firstName;
    private String lastName;
}
