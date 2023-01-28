package com.semicolon.ewallet.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpResponse {
    String firstName;
    String lastName;
    String emailAddress;
    String token;

}
