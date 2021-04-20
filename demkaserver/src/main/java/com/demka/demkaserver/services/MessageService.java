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

/**
 * Сервис для работы с сообщениями
 */
@Service
public class MessageService {

    private final MessageRepository messageRepo;

    @Autowired
    public MessageService(MessageRepository messageRepo) {
        this.messageRepo = messageRepo;
    }

    /**
     * Отдельный метод для получения последнего сообщения пользователя по времени
     *
     * @param userId - идентификатор пользователя
     * @return
     */
    public MessageDBEntity GetLastMessageByUser(String userId) {

        Pageable pageLimit = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "time_created"));
        List<MessageDBEntity> userMessageList = messageRepo.findAllByUser(userId, pageLimit);
        if (userMessageList.size() != 1) {
            return null;
        }
        return userMessageList.get(0);
    }

    /**
     * Создание нового сообщения
     *
     * @param userId   - идентификатор пользователя, который отправил сообщение
     * @param userName - имя пользователя, который отправляет сообщение
     * @param text     - текст сообщения
     * @param roomId   - идентификатор комнаты, куда отправляется сообщение
     * @return
     */
    public MessageDBEntity create(String userId, String userName, String text, String roomId) {

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

    /**
     * Обновление сообщения
     *
     * @param oldObj - старый объект сообщения
     * @param newObj - новый оьъект сообщения
     */
    public void update(MessageDBEntity oldObj, MessageDBEntity newObj) {
        messageRepo.delete(oldObj);
        messageRepo.save(newObj);
    }


    /**
     * Получение новых сообщний по каждой комнате, время которых больше, чем указанное в ts
     *
     * @param roomsList - список комнат пользователя (предварительно вызывается в findUserRooms)
     * @param ts        - UNIX-время
     * @return
     */
    public List<MessageDBEntity> getNewMessagesByRooms(List<RoomDBEntity> roomsList, Long ts) {
        List<MessageDBEntity> messagesList = new ArrayList<>();

        for (RoomDBEntity room : roomsList) {
            messagesList.addAll(messageRepo.getNewMessagesByRoom(room.getId(), ts));
        }
        return messagesList;

    }

    /**
     * Получение всех сообщний по каждой комнате
     *
     * @param roomsList - список комнат пользователя (предварительно вызывается в findUserRooms)
     * @return
     */
    public List<MessageDBEntity> getAllMessagesByRooms(List<RoomDBEntity> roomsList) {
        List<MessageDBEntity> messagesList = new ArrayList<>();
        Pageable pageLimit = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.ASC, "time_created"));
        for (RoomDBEntity room : roomsList) {
            messagesList.addAll(messageRepo.findAllByRoomId(room.getId(), pageLimit));
        }
        return messagesList;
    }

    /**
     * Получение сообщений по конкретной комнате
     *
     * @param roomId - идентификатор комнаты
     * @return
     */
    public List<MessageDBEntity> findByRoom(String roomId) {
        Pageable pageLimit = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.ASC, "time_created"));
        return messageRepo.findAllByRoomId(roomId, pageLimit);
    }

    /**
     * Поиск сообщения по его id
     *
     * @param id - идентификатор сообщения
     * @return
     */
    public Optional<MessageDBEntity> find(String id) {
        return messageRepo.findById(id);
    }

    /**
     * Удаление сообщения по его объекту
     *
     * @param item - объект сообщения
     */
    public void delete(MessageDBEntity item) {
        messageRepo.delete(item);
    }

    /**
     * Поиск всех сообщений
     *
     * @return
     */
    public List<MessageDBEntity> findAll() {
        return messageRepo.findAll();
    }

}