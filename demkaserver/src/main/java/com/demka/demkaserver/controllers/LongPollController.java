package com.demka.demkaserver.controllers;

import com.demka.demkaserver.entities.database.LongPollDBEntity;
import com.demka.demkaserver.entities.database.MessageDBEntity;
import com.demka.demkaserver.entities.database.RoomDBEntity;
import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.services.LongPollService;
import com.demka.demkaserver.services.MessageService;
import com.demka.demkaserver.services.RoomService;
import com.demka.demkaserver.services.UserService;
import com.demka.demkaserver.utils.GenResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

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
     * @param key - ключ пользователя
     * @return
     */
    @GetMapping(value = "/getServer")
    public ResponseEntity<Map<String, Object>> getLongPollServer(@RequestParam String key) {

        //Получаем объект пользователя через key + проверка ключа
        Optional<UserDBEntity> userOptional = userService.findByKey(key);
        if (userOptional.isEmpty()){
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Не удалось авторизоваться по указанному ключу"), HttpStatus.FORBIDDEN);
        }

        UserDBEntity user = userOptional.get();
        String userId = user.getId();

        //Находим комнаты пользователя
        List<RoomDBEntity> roomsList = roomService.findUserRooms(userId);


        List<MessageDBEntity> messagesList = messageService.getAllMessagesByRooms(roomsList);

        if (messagesList.size() == 0){
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Нельзя организовать лонгпул без каких-либо существующих сообщений!"), HttpStatus.NOT_FOUND);
        }

        Comparator<MessageDBEntity> bufComparator = Comparator.comparing(MessageDBEntity::getTimeCreated);
        Comparator<MessageDBEntity> MyComparator = bufComparator.reversed();
        messagesList.sort(MyComparator);

        Long ts = messagesList.get(0).getTimeCreated();
        LongPollDBEntity newPoll = longPollService.create(userId, ts);
        return new ResponseEntity<>(GenResponseUtil.ResponseOK(newPoll), HttpStatus.OK);
    }


    /**
     * Получение данных для лонгпула по определенному ключу
     * @param url - URL лонгпула
     * @param key - ключ ЛОНГПУЛА (не пользователя)
     * @param ts - значение ts, пполученное через /getServer
     * @return
     * @throws InterruptedException
     */
    @GetMapping(value = "/updates/{url}")
    public ResponseEntity<Map<String, Object>> longPoll(@PathVariable String url, @RequestParam String key, @RequestParam Long ts) throws InterruptedException {

        //Проверка на существование url
        if (longPollService.findByUrl(url).isEmpty()){
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Указанного url не существует"), HttpStatus.NOT_FOUND);
        }

        //Проверка на существование ключа + url
        Optional<LongPollDBEntity> currentPollOptional = longPollService.findByKeyAndUrl(key, url);
        if (currentPollOptional.isEmpty()){
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Не удалось авторизоваться по указанному ключу"), HttpStatus.FORBIDDEN);
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
                //Удаляем лонгпул с СУБД
                longPollService.delete(currentPoll.getId());
                return new ResponseEntity<>(GenResponseUtil.ResponseError("Необходимо обновить лонгпул через /getServer"), HttpStatus.OK);
            }

            //Иначе просто крутимся в этом вечном цикле
            Thread.sleep(500);
        }

        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("updates", newMessages);
        bodyMap.put("ts", newTs);

        return new ResponseEntity<>(GenResponseUtil.ResponseOK(bodyMap), HttpStatus.OK);

    }

}
