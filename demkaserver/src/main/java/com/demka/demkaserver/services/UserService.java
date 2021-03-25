package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public void create(UserDBEntity item){
        userRepo.save(item);
    }

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

    public UserDBEntity checkAuth(String login, String password){
        Optional<UserDBEntity> result = userRepo.checkUserAuth(login, password);
        if (result.isPresent()){
            return result.get();
        }
        return null;
    }

}
