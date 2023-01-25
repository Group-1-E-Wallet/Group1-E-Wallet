package com.semicolon.ewallet.user;


import com.semicolon.ewallet.user.dto.LoginRequest;
import com.semicolon.ewallet.user.dto.ChangePasswordRequest;
import com.semicolon.ewallet.user.dto.SignUpRequest;
import com.semicolon.ewallet.user.dto.SignUpResponse;
import com.semicolon.ewallet.user.token.ResendTokenRequest;
import jakarta.mail.MessagingException;


public interface UserService{


    String login(LoginRequest loginRequest);
    String resetPassword(ChangePasswordRequest changePasswordRequest);
    SignUpResponse register(SignUpRequest signUpRequest) throws MessagingException;
    String resendToken(ResendTokenRequest resendTokenRequest) throws MessagingException;


}
