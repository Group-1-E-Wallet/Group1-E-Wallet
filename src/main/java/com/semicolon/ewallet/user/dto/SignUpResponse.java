package com.semicolon.ewallet.user.dto;

import lombok.Data;

@Data
public class SignUpResponse {
    String firstName;
    String lastName;
    String email;
    String token;

}
