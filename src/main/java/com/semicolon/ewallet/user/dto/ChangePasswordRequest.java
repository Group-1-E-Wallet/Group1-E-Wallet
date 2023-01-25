package com.semicolon.ewallet.user.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String id;
    private String emailAddress;
    private String password;
    private String newPassword;
}
