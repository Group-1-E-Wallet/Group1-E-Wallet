package com.semicolon.ewallet.kyc.card;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CardRepository extends MongoRepository<Card, String> {
    Optional<Card> findByCardNumberIgnoreCase(String cardNumber);
}
