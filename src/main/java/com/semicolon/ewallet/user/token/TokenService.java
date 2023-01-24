package com.semicolon.ewallet.user.token;

import java.util.Optional;

public interface TokenService{

    public void  saveConfirmationToken(Token token);

    public Optional<Token> getConfirmationToken(String token);
    public void deleteExpiredToken();
    public void setConfirmationToken(String token);
}
