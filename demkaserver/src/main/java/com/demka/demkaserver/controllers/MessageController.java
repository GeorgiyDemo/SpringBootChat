package com.demka.demkaserver.controllers;

import com.demka.demkaserver.entities.database.MessageDBEntity;
import com.demka.demkaserver.entities.database.RoomDBEntity;
import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.services.MessageService;
import com.demka.demkaserver.services.RoomService;
import com.demka.demkaserver.services.UserService;
import com.demka.demkaserver.utils.GenResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final RoomService roomService;
    private final UserService userService;
    private final MessageService messageService;

    @Autowired
    public MessageController(RoomService roomService, UserService userService, MessageService messageService) {
        this.roomService = roomService;
        this.userService = userService;
        this.messageService = messageService;
    }

    /**
     * Отправка сообщения в опеределенную комнату roomId
     *
     * @param data - данные в JSON. Поля:
     *             key - ключ пользователя
     *             text - текст сообщения
     *             roomId - id комнаты, куда отправляется сообщение
     * @return
     */
    @PostMapping(value = "/send")
    public ResponseEntity<Map<String, Object>> sendMessage(@RequestBody Map<String, String> data) {

        String key = data.get("key");
        String messageText = data.get("text");
        String roomId = data.get("roomId");

        //Проверка переданных полей
        if ((key == null) || (messageText == null) || (roomId == null)) {
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Не все значения были переданы"), HttpStatus.BAD_REQUEST);
        }

        //Получаем объект пользователя через key + проверка ключа
        Optional<UserDBEntity> roomUserOptional = userService.findByKey(key);
        if (roomUserOptional.isEmpty()) {
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Не удалось авторизоваться по указанному ключу"), HttpStatus.FORBIDDEN);
        }

        //Проверка на комнату
        Optional<RoomDBEntity> currentRoomOptional = roomService.find(roomId);
        if (currentRoomOptional.isEmpty()) {
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Комнаты с id " + roomId + " не существует"), HttpStatus.BAD_REQUEST);
        }

        RoomDBEntity currentRoom = currentRoomOptional.get();
        UserDBEntity roomUser = roomUserOptional.get();

        //Проверка на то, может ли пользователь вообще писать в эту комнату
        if (!currentRoom.getUsers().contains(roomUser.getId())) {
            return new ResponseEntity<>(GenResponseUtil.ResponseError("У вас нет прав на отправку сообщения в эту комнату"), HttpStatus.FORBIDDEN);
        }

        MessageDBEntity newMessage = messageService.create(roomUser.getId(), roomUser.getName(), messageText, roomId);
        return new ResponseEntity<>(GenResponseUtil.ResponseOK(newMessage), HttpStatus.OK);
    }

    /**
     * Получение истории сообщений определенной комнаты
     *
     * @param key    - ключ пользователя
     * @param roomId - id комнаты
     * @return
     */
    @GetMapping(value = "/get")
    public ResponseEntity<Map<String, Object>> getMessagesByRoom(@RequestParam String key, @RequestParam String roomId) {

        //Проверка ключа
        Optional<UserDBEntity> currentUserOptional = userService.findByKey(key);
        if (currentUserOptional.isEmpty()) {
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Не удалось авторизоваться по указанному ключу"), HttpStatus.FORBIDDEN);
        }
        UserDBEntity currentUser = currentUserOptional.get();

        //Проверка на существование комнаты
        Optional<RoomDBEntity> currentRoomOptional = roomService.find(roomId);
        if (currentRoomOptional.isEmpty()) {
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Комнаты с id " + roomId + " не существует"), HttpStatus.BAD_REQUEST);
        }

        RoomDBEntity currentRoom = currentRoomOptional.get();
        //Проверка на то, имеет ли пользователь право на чтение сообщений из этой комнаты
        if (!currentRoom.getUsers().contains(currentUser.getId())) {
            return new ResponseEntity<>(GenResponseUtil.ResponseError("У вас нет прав на чтение сообщений из этой комнаты"), HttpStatus.FORBIDDEN);
        }

        //Если комната существует и все фильтрации пройдены
        List<MessageDBEntity> messagesList = messageService.findByRoom(roomId);
        return new ResponseEntity<>(GenResponseUtil.ResponseOK(messagesList), HttpStatus.OK);
    }

    /**
     * Удаление сообщения.
     * Сообщение может удалять либо сам пользователь, либо создатель комнаты
     *
     * @param data - данные в JSON. Поля:
     *             messageId - идентификатор сообщения для удаления
     *             key - ключ API
     * @return
     */
    @DeleteMapping(value = "/remove")
    public ResponseEntity<Map<String, Object>> removeMessage(@RequestBody Map<String, String> data) {

        String key = data.get("key");
        String messageId = data.get("messageId");

        if ((key == null) || (messageId == null)) {
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Не все значения были переданы"), HttpStatus.BAD_REQUEST);
        }

        //Проверка на авторизацию API
        Optional<UserDBEntity> currentUserOptional = userService.findByKey(key);
        if (currentUserOptional.isEmpty())
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Не удалось авторизоваться по указанному ключу"), HttpStatus.FORBIDDEN);
        UserDBEntity currentUser = currentUserOptional.get();

        //Проверка на существование сообщения
        Optional<MessageDBEntity> currentMessageOptional = messageService.find(messageId);
        if (currentMessageOptional.isEmpty())
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Сообщения с id " + messageId + " не существует"), HttpStatus.BAD_REQUEST);

        MessageDBEntity currentMessage = currentMessageOptional.get();
        //Проверка на возможность удаления сообщения
        Optional<RoomDBEntity> currentMessageRoomOptional = roomService.find(currentMessage.getRoomId());

        //Удаление от имени создателя беседы
        if (currentMessageRoomOptional.isPresent() && (currentUser.getId().equals(currentMessageRoomOptional.get().getCreatorId()))) {
            messageService.delete(currentMessage);
            System.out.println("Произошло удаление от имени создателя беседы");
            return new ResponseEntity<>(GenResponseUtil.ResponseOK("Успешное удаление сообщения " + messageId), HttpStatus.OK);
            //Удаление от имени владельца сообщения
        } else if (currentMessage.getUserId().equals(currentUser.getId())) {
            messageService.delete(currentMessage);
            System.out.println("Произошло удаление от имени владельца сообщения");
            return new ResponseEntity<>(GenResponseUtil.ResponseOK("Успешное удаление сообщения " + messageId), HttpStatus.OK);
            //Нет прав на удаление
        } else {
            System.out.println("Нет прав на удаление сообщения");
            return new ResponseEntity<>(GenResponseUtil.ResponseError("У вас нет права на удаление сообщения " + messageId), HttpStatus.FORBIDDEN);
        }
    }
}
