package com.demka.demkaserver.controllers;

import com.demka.demkaserver.entities.converters.UserConverter;
import com.demka.demkaserver.entities.database.RoomDBEntity;
import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.services.RoomService;
import com.demka.demkaserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    /**
     * TODO: Формирование сообщения, что комната была создана!
     * Создание комнаты для общения пользователей
     * @param data
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<HashMap<String, Object>> createRoom(@RequestBody Map<String, String> data) {

        HashMap<String, Object> map = new HashMap<>();
        List<String> users = Arrays.asList(data.get("users").split(","));
        String roomName = data.get("room_name");
        String key = data.get("key");

        //Получаем id создателя через key + проверка ключа
        Optional<UserDBEntity> creatorUser = userService.findByKey(key);
        //Если вдруг пользователь с key не найден
        if (creatorUser.isEmpty()){
            map.put("result", false);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }

        //Проверка на существование пользователей
        for (String userId: users) {
            if (userService.find(userId).isEmpty()){
                map.put("result", false);
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }
        }

        String creatorId = creatorUser.get().getId();

        RoomDBEntity newRoom = roomService.create(creatorId, roomName, users);
        map.put("result", true);
        map.put("body", newRoom);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * Получение инфорации о комнате
     * @param key
     * @param roomId
     * @return
     */
    @GetMapping(value = "/get")
    public ResponseEntity<HashMap<String, Object>> getRoomInfo(@RequestParam String key, @RequestParam String roomId) {

        HashMap<String, Object> map = new HashMap<>();

        //Проверка ключа
        if (!userService.checkUserKey(key)){
            map.put("result", false);
            return new ResponseEntity(map, HttpStatus.UNAUTHORIZED);
        }

        Optional<RoomDBEntity> roomResult = roomService.find(roomId);

        //Если комнаты с таким id не существует
        if (roomResult.isEmpty()){
            map.put("result", false);
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

        //Если существует
        map.put("result", true);
        map.put("body", roomResult.get());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
