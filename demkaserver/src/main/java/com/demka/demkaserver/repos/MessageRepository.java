package com.demka.demkaserver.repos;

import com.demka.demkaserver.entities.database.MessageDBEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Интерфейс MongoRepository для работы c коллекцией сообщений
 */
public interface MessageRepository extends MongoRepository<MessageDBEntity, String> {

    @Query("{ 'room_id' : ?0}")
    List<MessageDBEntity> findAllByRoomId(String roomId);

    @Query("{ 'user_id' : ?0}")
    List<MessageDBEntity> findAllByUser(String userId, Pageable pageable);

    @Query("{ 'room_id': ?0, 'time_created': {'$gt': ?1}}")
    List<MessageDBEntity> getNewMessagesByRoom(String roomId, Long ts);

}