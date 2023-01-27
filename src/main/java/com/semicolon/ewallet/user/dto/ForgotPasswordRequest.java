package com.semicolon.ewallet.user.dto;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String email;
    private String token;
}
