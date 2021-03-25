package com.demka.demkaserver.repos;

import com.demka.demkaserver.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    @Query("{ 'login' : ?0, 'password' : ?1}")
    public Optional<UserEntity> findAllDocuments(String login, String password);
}