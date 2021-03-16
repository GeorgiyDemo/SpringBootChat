package com.example.mongodb_example.repositories;

import com.example.mongodb_example.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<UserModel, String> {


}
