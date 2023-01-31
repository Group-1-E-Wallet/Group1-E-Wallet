package com.semicolon.ewallet.user;
import com.semicolon.ewallet.kyc.card.CardRequest;
import com.semicolon.ewallet.user.dto.*;
import com.semicolon.ewallet.user.dto.ResendTokenRequest;
import com.semicolon.ewallet.registration.dto.LoginRequest;
import com.semicolon.ewallet.user.dto.ChangePasswordRequest;
import com.semicolon.ewallet.registration.dto.SignUpRequest;
import com.semicolon.ewallet.registration.dto.SignUpResponse;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.util.Optional;


public interface UserService{



    String changePassword(ChangePasswordRequest changePasswordRequest);
    User getUser(User user);
   Optional<User> getByEmailAddress(String emailAddress);
    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws MessagingException;
    String resetPassword(ResetPasswordRequest resetPasswordRequest);
    void enableUser(String email);
     String completeRegistration(CompleteRegistrationRequest completeRegistrationRequest);
    void validateBvn(AddAccountRequest addAccountRequest) throws IOException;
    String validateAccount(CardRequest cardRequest) throws IOException;
    String generateToken (User user);



}
