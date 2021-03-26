package com.demka.demkaserver.entities.converters;

import com.demka.demkaserver.entities.database.UserDBEntity;
import com.demka.demkaserver.entities.response.UserAuthResponseEntity;
import com.demka.demkaserver.entities.response.UserSearchResponseEntity;


public class UserSearchConverter {

    public static UserSearchResponseEntity convert(UserDBEntity dbEntity){
        UserSearchResponseEntity responseEntity = new UserSearchResponseEntity();

        responseEntity.setId(dbEntity.getId());
        responseEntity.setName(dbEntity.getName());
        responseEntity.setTimeCreated(dbEntity.getTimeCreated());

        return responseEntity;
    }
}
