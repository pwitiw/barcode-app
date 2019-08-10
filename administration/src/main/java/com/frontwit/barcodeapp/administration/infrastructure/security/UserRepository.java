package com.frontwit.barcodeapp.administration.infrastructure.security;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String name);
}
