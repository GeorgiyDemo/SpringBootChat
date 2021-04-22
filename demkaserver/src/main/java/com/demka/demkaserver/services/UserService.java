package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.entities.request.UpdatePasswordEntity;
import com.demka.demkaserver.repos.UserRepository;
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
 * Сервис для работы с пользователями
 */
@Service
public class UserService {

    private final UserRepository userRepo;

    /**
     * Instantiates a new User service.
     *
     * @param userRepo the user repo
     */
    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Обновление пароля пользователя
     *
     * @param user    - объект пользователя
     * @param newData - объект обновления пароля (да, объект)
     */
    public void update(UserDBEntity user, UpdatePasswordEntity newData) {
        user.setPassword(newData.getNewPassword());
        userRepo.save(user);
    }

    /**
     * Поиск пользователя по его id
     *
     * @param id - идентификатор пользователя
     * @return optional
     */
    public Optional<UserDBEntity> find(String id) {
        return userRepo.findById(id);
    }

    /**
     * Поиск пользователя по мастер-ключу и e-mail
     * Используется при восстановлении пароля
     *
     * @param masterKey - мастер-ключ пользователя
     * @param login     - логин пользоваетля
     * @return optional
     */
    public Optional<UserDBEntity> findByMasterKeyAndEmail(String masterKey, String login) {
        return userRepo.findByMasterKeyAndEmail(masterKey, login);
    }

    /**
     * Проверка на авторизацию пользователя по паре логин/пароль
     *
     * @param login    - логин пользователя
     * @param password - пароль пользователя
     * @return user db entity
     */
    public UserDBEntity checkAuth(String login, String password) {
        Optional<UserDBEntity> result = userRepo.checkUserAuth(login, password);
        return result.orElse(null);
    }

    /**
     * Проверка на авторизацию пользователя по ключу API
     *
     * @param key - ключ API пользователя
     * @return user db entity
     */
    public UserDBEntity checkAuth(String key) {
        Optional<UserDBEntity> result = userRepo.checkUserKey(key);
        return result.orElse(null);
    }

    /**
     * Создание/регистрация пользователя в системе
     *
     * @param login          - логин пользователя (e-mail)
     * @param password       - пароль пользоваетля
     * @param username       - ник пользователя (не путать с логином)
     * @param masterPassword - мастер-ключ пользователя. Нужен для восстановления пароля.
     * @return user db entity
     */
    public UserDBEntity create(String login, String password, String username, String masterPassword) {

        //Проверяем на то, не зарегался ли уже пользователь с таким email или именем
        if ((userRepo.findByLogin(login).isPresent()) || (userRepo.findByName(username).isPresent())) {
            return null;
        }

        UserDBEntity newUser = new UserDBEntity();

        newUser.setLogin(login);
        newUser.setPassword(password);
        newUser.setMasterKey(masterPassword);
        newUser.setName(username);

        newUser.setKey(UUIDUtil.newKey());
        newUser.setTimeCreated(TimeUtil.unixTime());
        newUser.setId(UUIDUtil.newId());

        userRepo.save(newUser);
        return newUser;
    }

    /**
     * Поиск пользователя по ключу API
     *
     * @param key - ключ API пользователя
     * @return optional
     */
    public Optional<UserDBEntity> findByKey(String key) {
        return userRepo.checkUserKey(key);
    }

    /**
     * Поиск пользователей в системе. Используется при создании комнаты и выборе её участников
     *
     * @param name           - паттерн имени пользователя
     * @param limit          - лимит найденный пользователей
     * @param currentUserKey - ключ API пользователя, осуществляющего поиск
     * @return list
     */
    public List<UserDBEntity> searchUsers(String name, Integer limit, String currentUserKey) {

        List<UserDBEntity> bufList = new ArrayList<>();
        List<UserDBEntity> resultList = new ArrayList<>();
        Pageable pageLimit = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "time_created"));

        if (name != null) {
            bufList.addAll(userRepo.findAllByNameLimit("" + name, pageLimit));
        } else {
            bufList.addAll(userRepo.findAllLimit(pageLimit));
        }

        //Выкидываем самого пользователя, который запросил данные (чтоб не отображать ему самого себя)
        for (UserDBEntity item : bufList) {
            if (!item.getKey().equals(currentUserKey)) {
                resultList.add(item);
            }
        }
        return resultList;
    }

    /**
     * Проверка на существование ключа API пользователя
     *
     * @param key - ключ API пользователя
     * @return boolean
     */
    public boolean checkUserKey(String key) {
        return userRepo.checkUserKey(key).isPresent();
    }

    /**
     * Удаление пользователя по его объекту
     *
     * @param item - объект пользователя
     */
    public void delete(UserDBEntity item) {
        userRepo.delete(item);
    }

    /**
     * Поиск всех пользователей в коллекции
     *
     * @return list
     */
    public List<UserDBEntity> findAll() {
        return userRepo.findAll();
    }
}
