package com.semicolon.ewallet.registration.dto;


import lombok.AllArgsConstructor;

import lombok.Data;

@Data
public class SignUpResponse {
    String firstName;
    String lastName;
    String emailAddress;
    String token;

}
