package com.demka.demkaserver.repos;

import com.demka.demkaserver.entities.database.UserDBEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserDBEntity, String> {


    @Query("{ 'login' : ?0, 'password' : ?1}")
    public Optional<UserDBEntity> checkUserAuth(String login, String password);

    @Query("{ 'login' : ?0}")
    public Optional<UserDBEntity> findAllByLogin(String login);

    @Query("{ 'name' : ?0}")
    public Optional<UserDBEntity> findAllByName(String name);

}