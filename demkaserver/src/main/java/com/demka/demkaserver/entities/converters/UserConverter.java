package com.demka.demkaserver.entities.converters;

import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.entities.response.UserAuthResponseEntity;

public class UserConverter {

    public static UserAuthResponseEntity convert(UserDBEntity dbEntity){
        UserAuthResponseEntity responseEntity = new UserAuthResponseEntity();
        responseEntity.setId(dbEntity.getId());
        responseEntity.setName(dbEntity.getName());
        responseEntity.setKey(dbEntity.getKey());
        responseEntity.setTimeCreated(dbEntity.getTimeCreated());
        return responseEntity;
    }
}
