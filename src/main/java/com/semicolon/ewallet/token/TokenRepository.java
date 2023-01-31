package com.semicolon.ewallet.token;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {
    Optional<Token> findByToken(String token);

    void deleteTokenByExpiredAtBefore(LocalDateTime now);
    @Transactional
    void confirmedAt(LocalDateTime now, String token);
}
