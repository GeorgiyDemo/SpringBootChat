package com.demka.demkaserver.repos;

import com.demka.demkaserver.entities.database.MessageDBEntity;
import com.demka.demkaserver.entities.database.UserDBEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MessageRepository extends MongoRepository<MessageDBEntity, String> {

    @Query("{ 'room_id' : ?0}")
    public List<MessageDBEntity> findAllByRoomId(String roomId);

    @Query("{ 'user_id' : ?0}")
    public List<MessageDBEntity> findAllByUser(String userId, Pageable pageable);

    @Query("{ 'room_id': ?0, 'time_created': {'$gt': ?1}}")
    public List<MessageDBEntity> getNewMessagesByRoom(String roomId, Long ts);

}