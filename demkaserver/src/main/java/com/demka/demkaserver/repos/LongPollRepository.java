package com.demka.demkaserver.repos;

import com.demka.demkaserver.entities.database.LongPollDBEntity;
import com.demka.demkaserver.entities.database.UserDBEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;


public interface LongPollRepository extends MongoRepository<LongPollDBEntity, String> {

    @Query("{ 'url' : ?0}")
    public Optional<LongPollDBEntity> findByUrl(String url);

    @Query("{ 'key' : ?0, 'url' : ?1}")
    public Optional<LongPollDBEntity> findByKeyAndUrl(String key, String url);
}