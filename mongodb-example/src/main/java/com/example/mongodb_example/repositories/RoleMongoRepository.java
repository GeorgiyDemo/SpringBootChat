package com.example.mongodb_example.repositories;

import com.example.mongodb_example.models.RoleModel;
import com.example.mongodb_example.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface RoleMongoRepository extends MongoRepository<RoleModel, String> {


}
