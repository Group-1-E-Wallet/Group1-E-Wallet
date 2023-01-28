package com.semicolon.ewallet.kyc.card;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CardRepository extends MongoRepository<Card, String> {
    // Optional<Card> findByEmailAddressIgnoreCase(String emailAddress);

    Optional<Card> findCardById(Object id);
}
