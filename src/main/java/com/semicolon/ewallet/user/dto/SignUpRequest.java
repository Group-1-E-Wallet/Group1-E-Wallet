package com.semicolon.ewallet.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
}
