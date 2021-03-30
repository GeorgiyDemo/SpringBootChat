package com.demka.demkaserver.controllers;

import com.demka.demkaserver.entities.converters.UserConverter;
import com.demka.demkaserver.entities.converters.UserSearchConverter;
import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.entities.response.UserSearchResponseEntity;
import com.demka.demkaserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Авторизация пользователя
     * @param login - логин пользователя
     * @param password - пароль пользователя
     * @return
     */
    @GetMapping("/auth")
    public ResponseEntity<Map<String, Object>> auth(@RequestParam String login, @RequestParam String password) {
        Map<String, Object> map = new HashMap<>();
        UserDBEntity result = userService.checkAuth(login, password);

        if (result != null){
            map.put("result", true);
            map.put("body", UserConverter.convert(result));
        }
        else{
            map.put("result", false);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * Регистрация пользователя
     * @param data - данные в JSON. Поля:
     *             login - логин пользователя
     *             password - пароль пользователя
     *             username - ник пользователя
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> regUser(@RequestBody Map<String, String> data) {
        Map<String, Object> map = new HashMap<>();

        String login = data.get("login");
        String password = data.get("password");
        String username = data.get("username");

        if ((login == null) || (password == null) || (username == null)){
            map.put("result", false);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        UserDBEntity result = userService.create(login, password, username);
        if (result != null) {
            map.put("result", true);
            map.put("body", UserConverter.convert(result));
        }
        else{
            map.put("result", false);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    /**
     * Поиск пользователя в системе
     * @param key - ключ пользователя, выполняющего поиск
     * @param searchName - паттерн имени ника пользователя, которого ищем (необязательно)
     * @param limit - кол-во пользователей, которое надо вернуть, макс 200 (необязательно)
     * @return
     */
    @GetMapping(value = "/search")
    public ResponseEntity<Map<String, Object>> searchUser(@RequestParam String key, @RequestParam(required = false) String searchName, @RequestParam(required = false) Integer limit) {

        Map<String, Object> map = new HashMap<>();

        //Проверка ключа
        if (!userService.checkUserKey(key)){
            map.put("result", false);
            return new ResponseEntity(map, HttpStatus.FORBIDDEN);
        }

        if ((limit == null) || (limit > 200)){
            limit = 200;
        }

        List<UserDBEntity> bufList = userService.searchUsers(searchName, limit, key);

        //Если нет результатов
        if (bufList.size() == 0){
            map.put("result", false);
            return new ResponseEntity(map, HttpStatus.NOT_FOUND);
        }

        List<UserSearchResponseEntity> resultList = new ArrayList<UserSearchResponseEntity>();
        for (UserDBEntity item: bufList) {
            resultList.add(UserSearchConverter.convert(item));
        }

        map.put("result", true);
        map.put("body", resultList);
        return new ResponseEntity(map, HttpStatus.OK);
    }


}
