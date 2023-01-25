package com.semicolon.ewallet.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService{
    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void saveConfirmationToken(Token token){
        tokenRepository.save(token);
    }

    @Override
    public Optional<Token> getConfirmationToken(String token){
        return tokenRepository.findByToken(token);
    }

    @Override
    public void deleteExpiredToken( ){
        tokenRepository.deleteTokenByExpiredAtBefore(LocalDateTime.now());

    }

    @Override
    public void setConfirmationToken(String token){
        tokenRepository.setConfirmedAt(LocalDateTime.now(), token);

    }

}
