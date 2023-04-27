package com.semicolon.ewallet.registration.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String emailAddress;
    private String password;
}
