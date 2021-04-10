package com.demka.demkaserver.repos;

import com.demka.demkaserver.entities.database.RoomDBEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Интерфейс MongoRepository для работы c коллекцией чат-комнат
 */
public interface RoomRepository extends MongoRepository<RoomDBEntity, String> {

    @Query("{ 'users' : ?0}")
    List<RoomDBEntity> findAllByUser(String userId);
}