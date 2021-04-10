package com.demka.demkaserver.repos;

import com.demka.demkaserver.entities.database.UserDBEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс MongoRepository для работы c коллекцией пользователей
 */
public interface UserRepository extends MongoRepository<UserDBEntity, String> {


    @Query("{ 'key' : ?0}")
    Optional<UserDBEntity> checkUserKey(String key);

    @Query("{ 'login' : ?0, 'password' : ?1}")
    Optional<UserDBEntity> checkUserAuth(String login, String password);

    @Query("{ 'login' : ?0}")
    Optional<UserDBEntity> findByLogin(String login);

    @Query("{ 'name' : ?0}")
    Optional<UserDBEntity> findByName(String name);

    @Query("{ 'name' : { $regex: ?0 } }")
    List<UserDBEntity> findAllByNameLimit(String regexp, Pageable pageable);

    @Query("{}")
    List<UserDBEntity> findAllLimit(Pageable pageable);

    @Query("{ 'master_key' : ?0, 'login' : ?1}")
    Optional<UserDBEntity> findByMasterKeyAndEmail(String masterKey, String email);
}