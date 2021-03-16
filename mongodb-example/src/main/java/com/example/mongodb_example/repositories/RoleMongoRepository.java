package com.example.mongodb_example.repositories;

import com.example.mongodb_example.models.Role;
import com.example.mongodb_example.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleMongoRepository extends MongoRepository<Role, String> {


}
