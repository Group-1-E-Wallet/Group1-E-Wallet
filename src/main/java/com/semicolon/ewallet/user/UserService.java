package com.semicolon.ewallet.user;
import com.semicolon.ewallet.kyc.card.CardRequest;
import com.semicolon.ewallet.user.dto.*;
import com.semicolon.ewallet.user.dto.ResendTokenRequest;
import com.semicolon.ewallet.user.dto.LoginRequest;
import com.semicolon.ewallet.user.dto.ChangePasswordRequest;
import com.semicolon.ewallet.user.dto.SignUpRequest;
import com.semicolon.ewallet.user.dto.SignUpResponse;
import jakarta.mail.MessagingException;
import java.io.IOException;


public interface UserService{


    String login(LoginRequest loginRequest);
    String changePassword(ChangePasswordRequest changePasswordRequest);
    SignUpResponse register(SignUpRequest signUpRequest) throws MessagingException;

    User getByEmailAddress(String emailAddress);
    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws MessagingException;
    String resetPassword(ResetPasswordRequest resetPasswordRequest);
    void enableUser(String email);
    String tokenConfirmation(TokenConfirmationRequest tokenConfirmationRequest);
     String completeRegistration(CompleteRegistrationRequest completeRegistrationRequest) throws IOException;

    void validateBvn(AddAccountRequest addAccountRequest) throws IOException;
   //
    String resendToken(ResendTokenRequest resendTokenRequest) throws MessagingException;


}
