package com.learnspring.SecurityRest.repositories;

import com.learnspring.SecurityRest.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByRole(String role);
}
