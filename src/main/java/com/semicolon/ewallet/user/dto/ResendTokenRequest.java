package com.semicolon.ewallet.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ResendTokenRequest {
    @Email(message="input a valid email")
    private String emailAddress;
}
