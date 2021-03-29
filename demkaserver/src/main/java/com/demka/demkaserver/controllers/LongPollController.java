package com.demka.demkaserver.controllers;

import com.demka.demkaserver.entities.database.LongPollDBEntity;
import com.demka.demkaserver.entities.database.MessageDBEntity;
import com.demka.demkaserver.entities.database.RoomDBEntity;
import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.services.LongPollService;
import com.demka.demkaserver.services.MessageService;
import com.demka.demkaserver.services.RoomService;
import com.demka.demkaserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/longpoll")
public class LongPollController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private LongPollService longPollService;

    @Autowired
    private RoomService roomService;

    /**
     * Получение данных лонгпула для указанного ключа
     * @param key
     * @return
     */
    @GetMapping(value = "/getServer")
    public ResponseEntity<HashMap<String, Object>> getLongPollServer(@RequestParam String key) {
        HashMap<String, Object> map = new HashMap<>();

        //Получаем объект пользователя через key + проверка ключа
        Optional<UserDBEntity> userOptional = userService.findByKey(key);
        if (userOptional.isEmpty()){
            map.put("result", false);
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }

        UserDBEntity user = userOptional.get();
        String userId = user.getId();

        //Находим комнаты пользователя
        List<RoomDBEntity> roomsList = roomService.findUserRooms(userId);


        List<MessageDBEntity> messagesList = messageService.getAllMessagesByRooms(roomsList);

        if (messagesList.size() == 0){
            map.put("result", false);
            map.put("description", "Нельзя организовать лонгпул вообще без сообщений!");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

        Comparator<MessageDBEntity> bufComparator = Comparator.comparing(MessageDBEntity::getTimeCreated);
        Comparator<MessageDBEntity> MyComparator = bufComparator.reversed();
        messagesList.sort(MyComparator);

        Long ts = messagesList.get(0).getTimeCreated();
        LongPollDBEntity newPoll = longPollService.create(userId, ts);
        map.put("result", true);
        map.put("body", newPoll);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    /**
     * Получение данных для лонгпула по определенному ключу
     * @param url
     * @param key
     * @param ts
     * @return
     * @throws InterruptedException
     */
    @GetMapping(value = "/updates/{url}")
    public ResponseEntity<HashMap<String, Object>> longPoll(@PathVariable String url, @RequestParam String key, @RequestParam Long ts) throws InterruptedException {

        HashMap<String, Object> map = new HashMap<>();

        //Проверка на существование url
        if (longPollService.findByUrl(url).isEmpty()){
            map.put("result", false);
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

        //Проверка на существование ключа + url
        Optional<LongPollDBEntity> currentPollOptional = longPollService.findByKeyAndUrl(key, url);
        if (currentPollOptional.isEmpty()){
            map.put("result", false);
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }

        LongPollDBEntity currentPoll = currentPollOptional.get();

        //Время начала работы в цикле (чтоб ловить таймауты)
        Instant timeStarted = Instant.now();
        Long newTs;
        List<MessageDBEntity> newMessages;

        while (true){

            //Находим комнаты пользователя
            List<RoomDBEntity> roomsList = roomService.findUserRooms(currentPoll.getUserId());
            //Находим новые сообщения по каждой комнате
            newMessages = messageService.getNewMessagesByRooms(roomsList, ts);
            //Сортируем по новизне
            Comparator<MessageDBEntity> bufComparator = Comparator.comparing(MessageDBEntity::getTimeCreated);
            Comparator<MessageDBEntity> MyComparator = bufComparator.reversed();

            //Если есть какие-то обновления, то возвращаем эти обновления
            if (newMessages.size() != 0){
                newMessages.sort(MyComparator);
                newTs = newMessages.get(0).getTimeCreated();
                break;
            }

            //Если время кручения в цикле более одного часа - надо бы сделать переавторизацию клиента
            if (Duration.between(timeStarted, Instant.now()).toMinutes() > 60){
                map.put("result", false);
                //Удаляем лонгпул с СУБД
                longPollService.delete(currentPoll.getId());
                return new ResponseEntity<>(map, HttpStatus.OK);
            }

            //Иначе просто крутимся в этом вечном цикле
            Thread.sleep(500);
        }

        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("updates", newMessages);
        bodyMap.put("ts", newTs);

        map.put("result", true);
        map.put("body", bodyMap);
        return new ResponseEntity<>(map, HttpStatus.OK);

    }

}
