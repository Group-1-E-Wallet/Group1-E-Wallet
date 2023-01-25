package com.semicolon.ewallet.user.token;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {
    Optional<Token> findByToken(String token);

    void deleteTokenByExpiredAtBefore(LocalDateTime now);

    @Query("UPDATE ConfirmationToken confirmationToken " +
            "SET confirmationToken.confirmedAt = ?1 " +
            "WHERE confirmationToken.confirmedAt = ?2 ")
    @Transactional
    void setConfirmedAt(LocalDateTime now, String token);
}
