package com.demka.demkaserver.repos;

import com.demka.demkaserver.entities.database.LongPollDBEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

/**
 * Интерфейс MongoRepository для работы c коллекцией лонгпулов
 */
public interface LongPollRepository extends MongoRepository<LongPollDBEntity, String> {

    @Query("{ 'url' : ?0}")
    Optional<LongPollDBEntity> findByUrl(String url);

    @Query("{ 'key' : ?0, 'url' : ?1}")
    Optional<LongPollDBEntity> findByKeyAndUrl(String key, String url);
}