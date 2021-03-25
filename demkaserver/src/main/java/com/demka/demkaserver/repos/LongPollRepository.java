package com.demka.demkaserver.repos;

import com.demka.demkaserver.entities.database.LongPollDBEntity;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface LongPollRepository extends MongoRepository<LongPollDBEntity, String> {
}