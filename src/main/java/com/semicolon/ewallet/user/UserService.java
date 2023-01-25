package com.semicolon.ewallet.user;

import com.semicolon.ewallet.user.dto.ChangePasswordRequest;
import com.semicolon.ewallet.user.dto.SignUpRequest;
import jakarta.mail.MessagingException;

public interface UserService{
    String register(SignUpRequest signUpRequest) throws MessagingException;
    String resetPassword(ChangePasswordRequest changePasswordRequest);
}
