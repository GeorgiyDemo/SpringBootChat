package com.demka.demkaserver.repos;

import com.demka.demkaserver.entities.MessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<MessageEntity, String> {
}