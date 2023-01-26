package com.semicolon.ewallet.user.dto;

import lombok.Data;

@Data
public class TokenConfirmationRequest {
    private String token;
    private String email;
}
