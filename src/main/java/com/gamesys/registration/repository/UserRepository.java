package com.gamesys.registration.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.gamesys.registration.model.dao.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);
}
