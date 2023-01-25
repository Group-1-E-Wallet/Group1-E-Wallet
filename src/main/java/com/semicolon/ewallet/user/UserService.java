package com.semicolon.ewallet.user;

import com.semicolon.ewallet.user.dto.SignUpRequest;
import com.semicolon.ewallet.user.dto.SignUpResponse;
import jakarta.mail.MessagingException;

public interface UserService{
    SignUpResponse register(SignUpRequest signUpRequest) throws MessagingException;
}
