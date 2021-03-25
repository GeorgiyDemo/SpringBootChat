package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.RoomEntity;
import com.demka.demkaserver.entities.UserEntity;
import com.demka.demkaserver.repos.RoomRepository;
import com.demka.demkaserver.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepo;

    public void create(RoomEntity item){
        roomRepo.save(item);
    }

    public void update(RoomEntity oldObj, RoomEntity newObj) {
        roomRepo.delete(oldObj);
        roomRepo.save(newObj);
    }

    public void delete(RoomEntity item) { roomRepo.delete(item); }

    public List<RoomEntity> findAll(){
        return roomRepo.findAll();
    }

    public Optional<RoomEntity> find(String id){
        return roomRepo.findById(id);
    }

}
