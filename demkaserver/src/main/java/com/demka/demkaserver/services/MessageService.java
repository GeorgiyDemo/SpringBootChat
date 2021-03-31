package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.database.MessageDBEntity;
import com.demka.demkaserver.entities.database.RoomDBEntity;
import com.demka.demkaserver.repos.MessageRepository;
import com.demka.demkaserver.utils.TimeUtil;
import com.demka.demkaserver.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class MessageService {

    private final MessageRepository messageRepo;

    @Autowired
    public MessageService(MessageRepository messageRepo){
        this.messageRepo = messageRepo;
    }

    public MessageDBEntity GetLastMessageByUser(String userId){

        Pageable pageLimit = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "time_created"));
        List<MessageDBEntity> userMessageList = messageRepo.findAllByUser(userId, pageLimit);
        if (userMessageList.size() != 1){
            return null;
        }
        return userMessageList.get(0);
    }

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

    /**
     * Получение новых сообщний по каждой комнате, дата которых больше, чем ts
     * @param roomsList
     * @param ts
     * @return
     */
    public List<MessageDBEntity> getNewMessagesByRooms(List<RoomDBEntity> roomsList, Long ts){
        List<MessageDBEntity> messagesList = new ArrayList<MessageDBEntity>();

        for (RoomDBEntity room: roomsList) {
            messagesList.addAll(messageRepo.getNewMessagesByRoom(room.getId(), ts));
        }
        return messagesList;

    }

    /**
     * Получение всех сообщний по каждой комнате
     * @param roomsList
     * @return
     */
    public List<MessageDBEntity> getAllMessagesByRooms(List<RoomDBEntity> roomsList){
        List<MessageDBEntity> messagesList = new ArrayList<MessageDBEntity>();
        for (RoomDBEntity room: roomsList) {
            messagesList.addAll(messageRepo.findAllByRoomId(room.getId()));
        }
        return messagesList;
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