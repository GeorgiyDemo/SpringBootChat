package com.demka.demkaserver.repos;

import com.demka.demkaserver.entities.database.MessageDBEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<MessageDBEntity, String> {
}