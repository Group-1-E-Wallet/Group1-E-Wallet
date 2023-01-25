package com.semicolon.ewallet.user.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String emailAddress;
    private String password;
}
