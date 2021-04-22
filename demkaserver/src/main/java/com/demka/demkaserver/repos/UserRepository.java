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


    /**
     * Check user key optional.
     *
     * @param key the key
     * @return the optional
     */
    @Query("{ 'key' : ?0}")
    Optional<UserDBEntity> checkUserKey(String key);

    /**
     * Check user auth optional.
     *
     * @param login    the login
     * @param password the password
     * @return the optional
     */
    @Query("{ 'login' : ?0, 'password' : ?1}")
    Optional<UserDBEntity> checkUserAuth(String login, String password);

    /**
     * Find by login optional.
     *
     * @param login the login
     * @return the optional
     */
    @Query("{ 'login' : ?0}")
    Optional<UserDBEntity> findByLogin(String login);

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    @Query("{ 'name' : ?0}")
    Optional<UserDBEntity> findByName(String name);

    /**
     * Find all by name limit list.
     *
     * @param regexp   the regexp
     * @param pageable the pageable
     * @return the list
     */
    @Query("{ 'name' : { $regex: ?0 } }")
    List<UserDBEntity> findAllByNameLimit(String regexp, Pageable pageable);

    /**
     * Find all limit list.
     *
     * @param pageable the pageable
     * @return the list
     */
    @Query("{}")
    List<UserDBEntity> findAllLimit(Pageable pageable);

    /**
     * Find by master key and email optional.
     *
     * @param masterKey the master key
     * @param email     the email
     * @return the optional
     */
    @Query("{ 'master_key' : ?0, 'login' : ?1}")
    Optional<UserDBEntity> findByMasterKeyAndEmail(String masterKey, String email);
}