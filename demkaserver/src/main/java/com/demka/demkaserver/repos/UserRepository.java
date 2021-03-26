package com.demka.demkaserver.repos;

import com.demka.demkaserver.entities.database.UserDBEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<UserDBEntity, String> {


    @Query("{ 'key' : ?0}")
    public Optional<UserDBEntity> checkUserKey(String key);

    @Query("{ 'login' : ?0, 'password' : ?1}")
    public Optional<UserDBEntity> checkUserAuth(String login, String password);

    @Query("{ 'login' : ?0}")
    public Optional<UserDBEntity> findByLogin(String login);

    @Query("{ 'name' : ?0}")
    public Optional<UserDBEntity> findByName(String name);

    @Query("{ 'name' : { $regex: ?0 } }")
    public List<UserDBEntity> findAllByNameLimit(String regexp, Pageable pageable);

    @Query("{}")
    public List<UserDBEntity> findAllLimit(Pageable pageable);
}