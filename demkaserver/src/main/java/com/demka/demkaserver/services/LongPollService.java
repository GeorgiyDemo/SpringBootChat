package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.database.LongPollDBEntity;
import com.demka.demkaserver.repos.LongPollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LongPollService {

    @Autowired
    private LongPollRepository longPollRepo;

    public void create(LongPollDBEntity item){
        longPollRepo.save(item);
    }

    public void update(LongPollDBEntity oldObj, LongPollDBEntity newObj) {
        longPollRepo.delete(oldObj);
        longPollRepo.save(newObj);
    }

    public void delete(LongPollDBEntity item) { longPollRepo.delete(item); }

    public List<LongPollDBEntity> findAll(){
        return longPollRepo.findAll();
    }

    public Optional<LongPollDBEntity> find(String id){
        return longPollRepo.findById(id);
    }

}