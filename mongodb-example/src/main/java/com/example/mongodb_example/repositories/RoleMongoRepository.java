package com.example.mongodb_example.repositories;

import com.example.mongodb_example.models.RoleModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleMongoRepository extends MongoRepository<RoleModel, String> {


}
