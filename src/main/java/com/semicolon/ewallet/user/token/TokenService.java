package com.semicolon.ewallet.user.token;

import jakarta.mail.MessagingException;

import java.util.Optional;

public interface TokenService{
     void  saveConfirmationToken(Token token);

     Optional<Token> getConfirmationToken(String token);
     void deleteExpiredToken();
     void setConfirmationToken(String token);

}
