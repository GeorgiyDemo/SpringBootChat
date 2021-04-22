package com.demka.demkaserver.repos;

import com.demka.demkaserver.entities.database.LongPollDBEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

/**
 * Интерфейс MongoRepository для работы c коллекцией лонгпулов
 */
public interface LongPollRepository extends MongoRepository<LongPollDBEntity, String> {

    /**
     * Find by url optional.
     *
     * @param url the url
     * @return the optional
     */
    @Query("{ 'url' : ?0}")
    Optional<LongPollDBEntity> findByUrl(String url);

    /**
     * Find by key and url optional.
     *
     * @param key the key
     * @param url the url
     * @return the optional
     */
    @Query("{ 'key' : ?0, 'url' : ?1}")
    Optional<LongPollDBEntity> findByKeyAndUrl(String key, String url);
}