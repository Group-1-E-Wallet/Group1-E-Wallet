package com.semicolon.ewallet.kyc.card;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CardRepository extends MongoRepository<Card, String> {
    // Optional<Card> findByEmailAddressIgnoreCase(String emailAddress);

    //Optional<Card> findByid(Object id);
}
