package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.database.LongPollDBEntity;
import com.demka.demkaserver.repos.LongPollRepository;
import com.demka.demkaserver.utils.UUIDUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LongPollService {

    private final LongPollRepository longPollRepo;

    @Autowired
    public LongPollService(LongPollRepository longPollRepo){
        this.longPollRepo = longPollRepo;
    }

    public LongPollDBEntity create(String userId, Long ts){

        LongPollDBEntity newPoll = new LongPollDBEntity();
        newPoll.setId(UUIDUtil.newId());
        newPoll.setTs(ts);
        newPoll.setKey(UUIDUtil.newKey());
        newPoll.setUserId(userId);
        newPoll.setUrl(UUIDUtil.newURL());

        longPollRepo.save(newPoll);
        return newPoll;
    }

    public Optional<LongPollDBEntity> findByUrl(String url){
        return  longPollRepo.findByUrl(url);
    }

    public Optional<LongPollDBEntity> findByKeyAndUrl(String key, String url){
        return  longPollRepo.findByKeyAndUrl(key, url);
    }

    public void update(LongPollDBEntity oldObj, LongPollDBEntity newObj) {
        longPollRepo.delete(oldObj);
        longPollRepo.save(newObj);
    }

    public void delete(String pollId){
        longPollRepo.deleteById(pollId);
    }

    public void deleteAll(){
        longPollRepo.deleteAll();
    }

    public List<LongPollDBEntity> findAll(){
        return longPollRepo.findAll();
    }

    public Optional<LongPollDBEntity> find(String id){
        return longPollRepo.findById(id);
    }

}