package com.semicolon.ewallet.user.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    String emailAddress;
    String password;
    String token;
}
