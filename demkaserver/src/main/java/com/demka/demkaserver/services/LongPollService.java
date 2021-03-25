package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.LongPollEntity;
import com.demka.demkaserver.entities.LongPollEntity;
import com.demka.demkaserver.repos.LongPollRepository;
import com.demka.demkaserver.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LongPollService {

    @Autowired
    private LongPollRepository longPollRepo;

    public void create(LongPollEntity item){
        longPollRepo.save(item);
    }

    public void update(LongPollEntity oldObj, LongPollEntity newObj) {
        longPollRepo.delete(oldObj);
        longPollRepo.save(newObj);
    }

    public void delete(LongPollEntity item) { longPollRepo.delete(item); }

    public List<LongPollEntity> findAll(){
        return longPollRepo.findAll();
    }

    public Optional<LongPollEntity> find(String id){
        return longPollRepo.findById(id);
    }

}