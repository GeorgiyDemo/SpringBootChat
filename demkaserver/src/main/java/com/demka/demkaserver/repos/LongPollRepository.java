package com.demka.demkaserver.repos;

import com.demka.demkaserver.entities.LongPollEntity;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface LongPollRepository extends MongoRepository<LongPollEntity, String> {
}