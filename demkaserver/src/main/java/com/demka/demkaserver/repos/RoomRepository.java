package com.demka.demkaserver.repos;

import com.demka.demkaserver.entities.database.MessageDBEntity;
import com.demka.demkaserver.entities.database.RoomDBEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RoomRepository extends MongoRepository<RoomDBEntity, String> {

    @Query("{ 'users' : ?0}")
    public List<RoomDBEntity> findAllByUser(String userId);
}