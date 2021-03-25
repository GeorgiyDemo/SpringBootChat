package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.MessageEntity;
import com.demka.demkaserver.repos.MessageRepository;
import com.demka.demkaserver.repos.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepo;

    public void create(MessageEntity item){
        messageRepo.save(item);
    }

    public void update(MessageEntity oldObj, MessageEntity newObj) {
        messageRepo.delete(oldObj);
        messageRepo.save(newObj);
    }

    public void delete(MessageEntity item) { messageRepo.delete(item); }

    public List<MessageEntity> findAll(){
        return messageRepo.findAll();
    }

    public Optional<MessageEntity> find(String id){
        return messageRepo.findById(id);
    }

}