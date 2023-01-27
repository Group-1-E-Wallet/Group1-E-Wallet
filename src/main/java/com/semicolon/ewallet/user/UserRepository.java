package com.semicolon.ewallet.user;

import com.semicolon.ewallet.kyc.Identification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
    Optional<User> findByEmailAddressIgnoreCase(String email);
    Optional<User> findUserByPassword(String password);
    User findUserById(String id);
}
