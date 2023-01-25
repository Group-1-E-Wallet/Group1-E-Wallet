package com.semicolon.ewallet.user.dto;

import lombok.Data;

@Data
public class SignUpResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String token;
}
