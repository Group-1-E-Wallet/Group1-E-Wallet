package com.semicolon.ewallet.user;

import com.semicolon.ewallet.user.dto.SignUpRequest;
import com.semicolon.ewallet.user.token.dtos.ResendTokenRequest;
import jakarta.mail.MessagingException;

public interface UserService{
    String register(SignUpRequest signUpRequest) throws MessagingException;
    String resendToken(ResendTokenRequest resendTokenRequest) throws MessagingException;

}
