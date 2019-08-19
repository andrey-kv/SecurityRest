package com.learnspring.SecurityRest.repositories;

import com.learnspring.SecurityRest.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);
    User findByFullname(String fullName);
}
