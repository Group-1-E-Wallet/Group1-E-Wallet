package com.semicolon.ewallet.registration;

import com.semicolon.ewallet.registration.dto.LoginRequest;
import com.semicolon.ewallet.registration.dto.SignUpRequest;
import com.semicolon.ewallet.registration.dto.SignUpResponse;
import com.semicolon.ewallet.user.User;
import com.semicolon.ewallet.user.dto.ResendTokenRequest;
import com.semicolon.ewallet.user.dto.TokenConfirmationRequest;
import jakarta.mail.MessagingException;

public interface RegistrationService {
    SignUpResponse register(SignUpRequest signUpRequest) throws MessagingException;
    String tokenConfirmation(TokenConfirmationRequest tokenConfirmationRequest);
    String resendToken(ResendTokenRequest resendTokenRequest) throws MessagingException;
    String login(LoginRequest loginRequest);
    String generateToken (User user);

}
