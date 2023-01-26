package com.semicolon.ewallet.user;

import com.semicolon.ewallet.kyc.card.CardRequest;
import com.semicolon.ewallet.kyc.Identification;
import com.semicolon.ewallet.user.dto.AddAccountRequest;
import com.semicolon.ewallet.user.dto.CompleteRegistrationRequest;
import com.semicolon.ewallet.user.dto.SignUpRequest;
import com.semicolon.ewallet.user.dto.SignUpResponse;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface UserService{
    SignUpResponse register(SignUpRequest signUpRequest) throws MessagingException;

    User getByEmailAddress(String emailAddress);

     User getIdentification(Identification id);
     String completeRegistration(CompleteRegistrationRequest completeRegistrationRequest);

    void validateBvn(AddAccountRequest addAccountRequest) throws IOException;
    String validateAccount(CardRequest cardRequest) throws IOException;
}
