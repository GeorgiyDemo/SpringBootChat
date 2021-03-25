package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.database.MessageDBEntity;
import com.demka.demkaserver.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepo;

    public void create(MessageDBEntity item){
        messageRepo.save(item);
    }

    public void update(MessageDBEntity oldObj, MessageDBEntity newObj) {
        messageRepo.delete(oldObj);
        messageRepo.save(newObj);
    }

    public void delete(MessageDBEntity item) { messageRepo.delete(item); }

    public List<MessageDBEntity> findAll(){
        return messageRepo.findAll();
    }

    public Optional<MessageDBEntity> find(String id){
        return messageRepo.findById(id);
    }

}