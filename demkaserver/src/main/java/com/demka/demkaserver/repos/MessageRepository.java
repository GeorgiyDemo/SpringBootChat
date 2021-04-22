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

    /**
     * Find all by room id list.
     *
     * @param roomId   the room id
     * @param pageable the pageable
     * @return the list
     */
    @Query("{ 'room_id' : ?0}")
    List<MessageDBEntity> findAllByRoomId(String roomId, Pageable pageable);

    /**
     * Find all by user list.
     *
     * @param userId   the user id
     * @param pageable the pageable
     * @return the list
     */
    @Query("{ 'user_id' : ?0}")
    List<MessageDBEntity> findAllByUser(String userId, Pageable pageable);

    /**
     * Gets new messages by room.
     *
     * @param roomId the room id
     * @param ts     the ts
     * @return the new messages by room
     */
    @Query("{ 'room_id': ?0, 'time_created': {'$gt': ?1}}")
    List<MessageDBEntity> getNewMessagesByRoom(String roomId, Long ts);

}