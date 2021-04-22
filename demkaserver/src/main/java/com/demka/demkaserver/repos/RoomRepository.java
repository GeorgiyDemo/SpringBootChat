package com.demka.demkaserver.repos;

import com.demka.demkaserver.entities.database.RoomDBEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Интерфейс MongoRepository для работы c коллекцией чат-комнат
 */
public interface RoomRepository extends MongoRepository<RoomDBEntity, String> {

    /**
     * Find all by user list.
     *
     * @param userId the user id
     * @return the list
     */
    @Query("{ 'users' : ?0}")
    List<RoomDBEntity> findAllByUser(String userId);
}