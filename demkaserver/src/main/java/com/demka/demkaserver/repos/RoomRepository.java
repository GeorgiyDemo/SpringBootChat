package com.demka.demkaserver.repos;

import com.demka.demkaserver.entities.RoomEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<RoomEntity, String> {
}