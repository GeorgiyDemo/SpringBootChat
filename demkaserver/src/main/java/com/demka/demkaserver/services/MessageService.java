package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.database.MessageDBEntity;
import com.demka.demkaserver.repos.MessageRepository;
import com.demka.demkaserver.utils.TimeUtil;
import com.demka.demkaserver.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepo;

    //Создание сообщения
    public MessageDBEntity create(String userId, String userName, String text, String roomId){

        MessageDBEntity newMessage = new MessageDBEntity();
        newMessage.setUserId(userId);
        newMessage.setUserName(userName);
        newMessage.setText(text);
        newMessage.setRoomId(roomId);

        newMessage.setTimeCreated(TimeUtil.unixTime());
        newMessage.setId(UUIDUtil.newId());

        messageRepo.save(newMessage);
        return newMessage;
    }

    public void update(MessageDBEntity oldObj, MessageDBEntity newObj) {
        messageRepo.delete(oldObj);
        messageRepo.save(newObj);
    }

    public void delete(MessageDBEntity item) { messageRepo.delete(item); }

    public List<MessageDBEntity> findByRoom(String roomId){
        return messageRepo.findAllByRoomId(roomId);
    }

    public List<MessageDBEntity> findAll(){
        return messageRepo.findAll();
    }

    public Optional<MessageDBEntity> find(String id){
        return messageRepo.findById(id);
    }

}