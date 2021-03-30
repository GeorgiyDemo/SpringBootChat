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
     * @param login
     * @param password
     * @return
     */
    @GetMapping("/auth")
    public ResponseEntity<HashMap<String, Object>> auth(@RequestParam String login, @RequestParam String password) {
        HashMap<String, Object> map = new HashMap<>();
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
     * @param data
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<HashMap<String, Object>> regUser(@RequestBody Map<String, String> data) {
        HashMap<String, Object> map = new HashMap<>();

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
     * @param key
     * @param searchName
     * @param limit
     * @return
     */
    @GetMapping(value = "/search")
    public ResponseEntity<HashMap<String, Object>> searchUser(@RequestParam String key, @RequestParam(required = false) String searchName, @RequestParam(required = false) Integer limit) {

        HashMap<String, Object> map = new HashMap<>();

        //Проверка ключа
        if (!userService.checkUserKey(key)){
            map.put("result", false);
            return new ResponseEntity(map, HttpStatus.FORBIDDEN);
        }

        if (limit == null){
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
