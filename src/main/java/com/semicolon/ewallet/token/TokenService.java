package com.semicolon.ewallet.token;

import java.util.Optional;

public interface TokenService{

    void  saveConfirmationToken(Token token);

    Optional<Token> getConfirmationToken(String token);
    void deleteExpiredToken();
    void setConfirmationToken(String token);
    //public String resendToken(String email, String link) throws MessagingException;


}
