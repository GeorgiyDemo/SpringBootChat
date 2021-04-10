package com.demka.demkaserver.controllers;

import com.demka.demkaserver.entities.converters.UserConverter;
import com.demka.demkaserver.entities.converters.UserSearchConverter;
import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.entities.request.UpdatePasswordEntity;
import com.demka.demkaserver.entities.response.UserAuthResponseEntity;
import com.demka.demkaserver.entities.response.UserSearchResponseEntity;
import com.demka.demkaserver.services.UserService;
import com.demka.demkaserver.utils.GenResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Авторизация пользователя через пару логина/пароля
     *
     * @param login    - логин пользователя
     * @param password - пароль пользователя
     * @return
     */
    @GetMapping(value = "/auth", params = {"login", "password"})
    public ResponseEntity<Map<String, Object>> auth(@RequestParam String login, @RequestParam String password) {

        UserDBEntity result = userService.checkAuth(login, password);

        if (result != null) {
            UserAuthResponseEntity user = UserConverter.convert(result);
            return new ResponseEntity<>(GenResponseUtil.ResponseOK(user), HttpStatus.OK);
        }
        return new ResponseEntity<>(GenResponseUtil.ResponseError("Не удалось войти с указанным логином/паролем"), HttpStatus.OK);
    }

    /**
     * Авторизация пользователя через ключ API
     *
     * @param key - ключ API
     * @return
     */
    @GetMapping(value = "/auth", params = {"key"})
    public ResponseEntity<Map<String, Object>> auth(@RequestParam String key) {

        UserDBEntity result = userService.checkAuth(key);
        if (result != null) {
            UserAuthResponseEntity user = UserConverter.convert(result);
            return new ResponseEntity<>(GenResponseUtil.ResponseOK(user), HttpStatus.OK);
        }
        return new ResponseEntity<>(GenResponseUtil.ResponseError("Не удалось войти с указанным ключем API"), HttpStatus.OK);
    }

    /**
     * Регистрация пользователя
     *
     * @param data - данные в JSON. Поля:
     *             login - логин пользователя
     *             password - пароль пользователя
     *             username - ник пользователя
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> regUser(@RequestBody Map<String, String> data) {

        String login = data.get("login");
        String password = data.get("password");
        String username = data.get("username");
        String masterKey = data.get("masterKey");

        //Если клиент передал не все значения
        if ((login == null) || (password == null) || (username == null) || (masterKey == null)) {
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Не все значения были переданы"), HttpStatus.BAD_REQUEST);
        }
        login = login.toLowerCase(Locale.ROOT);

        UserDBEntity result = userService.create(login, password, username, masterKey);
        //Если все хорошо
        if (result != null) {
            UserAuthResponseEntity user = UserConverter.convert(result);
            return new ResponseEntity<>(GenResponseUtil.ResponseOK(user), HttpStatus.OK);
        }
        //Если такой ник или e-mail занят
        return new ResponseEntity<>(GenResponseUtil.ResponseError("Пользователь с таким ником или e-mail уже существует"), HttpStatus.OK);
    }

    /**
     * Поиск пользователя в системе
     *
     * @param key        - ключ пользователя, выполняющего поиск
     * @param searchName - паттерн имени ника пользователя, которого ищем (необязательно)
     * @param limit      - кол-во пользователей, которое надо вернуть, макс 200 (необязательно)
     * @return
     */
    @GetMapping(value = "/search")
    public ResponseEntity<Map<String, Object>> searchUser(@RequestParam String key, @RequestParam(required = false) String searchName, @RequestParam(required = false) Integer limit) {

        //Проверка ключа
        if (!userService.checkUserKey(key)) {
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Не удалось авторизоваться по указанному ключу"), HttpStatus.FORBIDDEN);
        }

        if ((limit == null) || (limit > 200)) {
            limit = 200;
        }

        List<UserDBEntity> bufList = userService.searchUsers(searchName, limit, key);
        List<UserSearchResponseEntity> resultList = new ArrayList<>();
        for (UserDBEntity item : bufList) {
            resultList.add(UserSearchConverter.convert(item));
        }

        return new ResponseEntity<>(GenResponseUtil.ResponseOK(resultList), HttpStatus.OK);
    }

    /**
     * Восстановление (обновление) пароля пользователя по мастер-ключу
     * @param updateItem
     * @return
     */
    @PutMapping("/reset")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordEntity updateItem) {

        Optional<UserDBEntity> searchResult = userService.findByMasterKeyAndEmail(updateItem.getMasterKey(),updateItem.getEmail());
        //Если не нашли пользователей, которые связаны с переданным e-mail и мастер-ключем
        if (searchResult.isEmpty()){
            return new ResponseEntity<>(GenResponseUtil.ResponseError("Не удалось сменить пароль. Проверьте данные"), HttpStatus.FORBIDDEN);
        }
        UserDBEntity currentUser = searchResult.get();
        userService.update(currentUser, updateItem);
        return new ResponseEntity<>(GenResponseUtil.ResponseOK(UserSearchConverter.convert(currentUser)), HttpStatus.OK);
    }
}
