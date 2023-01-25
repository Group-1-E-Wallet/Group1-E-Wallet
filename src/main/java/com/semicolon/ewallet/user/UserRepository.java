package com.semicolon.ewallet.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
    Optional<User> findByEmailIgnoreCase(String email);
    User findUserByEmailIgnoreCase(String email);
    Optional<User> findUserByPassword(String password);

    User findUserById(String id);
}
