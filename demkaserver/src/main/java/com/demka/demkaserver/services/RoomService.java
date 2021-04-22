package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.database.RoomDBEntity;
import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.repos.RoomRepository;
import com.demka.demkaserver.utils.TimeUtil;
import com.demka.demkaserver.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с комнатами
 */
@Service
public class RoomService {

    private final RoomRepository roomRepo;

    @Autowired
    public RoomService(RoomRepository roomRepo) {
        this.roomRepo = roomRepo;
    }

    /**
     * Отдаёт список комнат, в которых состоит пользователь
     *
     * @param userId - идентификатор пользователя
     * @return
     */
    public List<RoomDBEntity> findUserRooms(String userId) {
        return roomRepo.findAllByUser(userId);
    }

    /**
     * Поиск комнаты по её id
     *
     * @param id - идентификатор комнаты
     * @return
     */
    public Optional<RoomDBEntity> find(String id) {
        return roomRepo.findById(id);
    }

    /**
     * Коздание комнаты
     *
     * @param creatorId   - идентификатор пользователя-создателя комнаты
     * @param roomName    - имя комнаты
     * @param usersBuffer - спосок идентификаторов пользователей-участников комнаты
     * @return
     */
    public RoomDBEntity create(String creatorId, String roomName, List<String> usersBuffer) {

        //Проверка, чтоб создатель также был в списке пользователей комнаты

        List<String> allUsers = new ArrayList<>(usersBuffer);

        if (!allUsers.contains(creatorId)) {
            allUsers.add(creatorId);
        }

        System.out.println();

        RoomDBEntity newRoom = new RoomDBEntity();
        newRoom.setCreatorId(creatorId);
        newRoom.setName(roomName);
        newRoom.setUsers(allUsers);
        newRoom.setId(UUIDUtil.newId());
        newRoom.setTimeCreated(TimeUtil.unixTime());

        roomRepo.save(newRoom);
        return newRoom;
    }

    /**
     * Обновление комнаты
     *
     * @param oldObj - старый объект комнаты
     * @param newObj - новый объект комнаты
     */
    public void update(RoomDBEntity oldObj, RoomDBEntity newObj) {
        roomRepo.delete(oldObj);
        roomRepo.save(newObj);
    }

    /**
     * Удаление комнаты по её объекту
     *
     * @param room - объект комнаты
     */
    public void delete(RoomDBEntity room) {
        roomRepo.delete(room);
    }

    /**
     * Поиск всех комнат
     *
     * @return
     */
    public List<RoomDBEntity> findAll() {
        return roomRepo.findAll();
    }

    /**
     * Удаление пользователя из комнаты
     *
     * @param room   - объект комнаты
     * @param userId - объект пользователя для удаления
     */
    public void removeUser(RoomDBEntity room, String userId) {
        room.getUsers().remove(userId);
        roomRepo.save(room);
    }

    /**
     * Удаление пользователя из комнаты
     *
     * @param room - объект комнаты
     * @param user - объект пользователя для удаления
     */
    public void removeUser(RoomDBEntity room, UserDBEntity user) {
        String userId = user.getId();
        room.getUsers().remove(userId);
        roomRepo.save(room);
    }

    /**
     * Добавление пользователя в комнату
     *
     * @param room   - объект комнаты
     * @param userId - идентификатор пользователя для добавления
     */
    public void addUser(RoomDBEntity room, String userId) {
        room.getUsers().add(userId);
        roomRepo.save(room);
    }

    /**
     * Добавление пользователя в комнату
     *
     * @param room - объект комнаты
     * @param user - объект пользователя для добавления
     */
    public void addUser(RoomDBEntity room, UserDBEntity user) {
        String userId = user.getId();
        room.getUsers().add(userId);
        roomRepo.save(room);
    }
}
