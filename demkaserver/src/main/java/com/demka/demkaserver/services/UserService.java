package com.demka.demkaserver.services;

import com.demka.demkaserver.entities.UserEntity;
import com.demka.demkaserver.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public void create(UserEntity item){
        userRepo.save(item);
    }

    public void update(UserEntity oldObj, UserEntity newObj) {
        userRepo.delete(oldObj);
        userRepo.save(newObj);
    }

    public void delete(UserEntity item) { userRepo.delete(item); }

    public List<UserEntity> findAll(){
        return userRepo.findAll();
    }

    public Optional<UserEntity> find(String id){
        return userRepo.findById(id);
    }

    public boolean checkAuth(String login, String password){
        Optional<UserEntity> result = userRepo.findAllDocuments(login, password);
        return result.isPresent();
    }

}
