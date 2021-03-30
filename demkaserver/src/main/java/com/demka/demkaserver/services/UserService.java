package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.database.UserDBEntity;
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

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;


    public void update(UserDBEntity oldObj, UserDBEntity newObj) {
        userRepo.delete(oldObj);
        userRepo.save(newObj);
    }

    public void delete(UserDBEntity item) { userRepo.delete(item); }

    public List<UserDBEntity> findAll(){
        return userRepo.findAll();
    }

    public Optional<UserDBEntity> find(String id){
        return userRepo.findById(id);
    }

    //Проверка на авторизацию пользователя
    public UserDBEntity checkAuth(String login, String password){
        Optional<UserDBEntity> result = userRepo.checkUserAuth(login, password);
        if (result.isPresent()){
            return result.get();
        }
        return null;
    }

    //Регистрация пользователя
    public UserDBEntity create(String login, String password, String username){

        //Проверяем на то, не зарегался ли уже пользователь с таким email или именем
        if ((userRepo.findByLogin(login).isPresent()) || (userRepo.findByName(username).isPresent())){
            return null;
        }

        Optional<UserDBEntity> result = userRepo.checkUserAuth(login, password);

        UserDBEntity newUser = new UserDBEntity();

        newUser.setLogin(login);
        newUser.setPassword(password);
        newUser.setName(username);

        newUser.setKey(UUIDUtil.newKey());
        newUser.setTimeCreated(TimeUtil.unixTime());
        newUser.setId(UUIDUtil.newId());

        userRepo.save(newUser);
        return newUser;
    }

    //Поиск по ключу
    public Optional<UserDBEntity> findByKey(String key){
        return userRepo.checkUserKey(key);
    }

    //Поиск пользователей
    public List<UserDBEntity> searchUsers(String name, Integer limit, String currentUserKey){

        List<UserDBEntity> bufList = new ArrayList<UserDBEntity>();
        List<UserDBEntity> resultList = new ArrayList<UserDBEntity>();
        Pageable pageLimit = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "time_created"));

        if (name != null){
            bufList.addAll(userRepo.findAllByNameLimit("^"+name, pageLimit));
        }
        else{
            bufList.addAll(userRepo.findAllLimit(pageLimit));
        }

        //Выкидываем самого пользователя, который запросил данные (чтоб не отображать ему самого себя)
        for (UserDBEntity item: bufList) {
            if (!item.getKey().equals(currentUserKey)){
                resultList.add(item);
            }
        }

        return resultList;
    }

    //Проверка на валидный ключ пользователя
    public boolean checkUserKey(String key){
        return userRepo.checkUserKey(key).isPresent();
    }

}
