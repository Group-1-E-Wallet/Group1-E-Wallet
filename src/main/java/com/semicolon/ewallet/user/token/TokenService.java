package com.semicolon.ewallet.user.token;


import java.util.Optional;

public interface TokenService{

     void  saveConfirmationToken(Token token);
    public Optional<Token> getConfirmationToken(String token);
    void deleteExpiredToken();
    public void setTokenConfirmationAt(String token);
}
