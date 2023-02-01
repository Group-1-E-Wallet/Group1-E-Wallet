package com.semicolon.ewallet.registration.dto;


import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class SignUpRequest {

    @NotBlank(message="field cannot be empty")
    private String firstName;
    @NotBlank(message="field cannot be empty")
    private String lastName;
    @Email(message="field is required")
    private String emailAddress;
    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*_?&])[A-Za-z\\d@$!%*_?&]{5,20}$",
    message="Password must include 1 Uppercase letter, 1 lowercase letter, 1 digit," +
            " 1 special character and minimum 5 characters")
    private String password;
}
