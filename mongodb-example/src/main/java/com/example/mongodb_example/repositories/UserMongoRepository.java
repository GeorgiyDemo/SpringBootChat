package com.example.mongodb_example.repositories;

import com.example.mongodb_example.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<User, String> {


}
