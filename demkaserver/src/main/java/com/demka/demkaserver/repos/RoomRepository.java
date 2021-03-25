package com.demka.demkaserver.repos;

import com.demka.demkaserver.entities.database.RoomDBEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<RoomDBEntity, String> {
}