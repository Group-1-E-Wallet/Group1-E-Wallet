package com.semicolon.ewallet.user;

import com.semicolon.ewallet.user.dto.LoginRequest;
import com.semicolon.ewallet.user.dto.SignUpRequest;
import jakarta.mail.MessagingException;

public interface UserService{
    String register(SignUpRequest signUpRequest) throws MessagingException;
    String login(LoginRequest loginRequest);
}
