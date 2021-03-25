package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.database.RoomDBEntity;
import com.demka.demkaserver.repos.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepo;

    public void create(RoomDBEntity item){
        roomRepo.save(item);
    }

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

}
