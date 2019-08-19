package com.learnspring.SecurityRest.repositories;

import com.learnspring.SecurityRest.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    @Override
    void delete(Product deleted);
}
