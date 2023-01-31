package com.semicolon.ewallet.user.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String emailAddress;
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
