package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.database.RoomDBEntity;
import com.demka.demkaserver.repos.RoomRepository;
import com.demka.demkaserver.utils.TimeUtil;
import com.demka.demkaserver.utils.UUIDUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepo;

    public void update(RoomDBEntity oldObj, RoomDBEntity newObj) {
        roomRepo.delete(oldObj);
        roomRepo.save(newObj);
    }

    public void delete(RoomDBEntity item) { roomRepo.delete(item); }

    public List<RoomDBEntity> findAll(){
        return roomRepo.findAll();
    }

    public Optional<RoomDBEntity> find(String id){
        return roomRepo.findById(id);
    }

    public RoomDBEntity create(String creatorId, String roomName, List<String> usersBuffer) {

        //Проверка, чтоб создатель также был в списке пользователей комнаты

        List<String> allUsers = new ArrayList<String>(usersBuffer);

        if (!allUsers.contains(creatorId)){
            allUsers.add(creatorId);
        }

        System.out.println();

        RoomDBEntity newRoom = new RoomDBEntity();
        newRoom.setCreatorId(creatorId);
        newRoom.setName(roomName);
        newRoom.setUsers(allUsers);
        newRoom.setId(UUIDUtil.newId());
        newRoom.setTimeCreated(TimeUtil.unixTime());

        roomRepo.save(newRoom);
        return newRoom;
    }
}
