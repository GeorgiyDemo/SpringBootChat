package com.demka.demkaserver.services;

import com.demka.demkaserver.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserServiceImpl implements UserService {

    private static final AtomicInteger PERSON_ID = new AtomicInteger();
    private static Map<Integer, User> REPOSITORY_MAP = new HashMap<>();

    @Override
    public User create(User person) {
        final int personId = PERSON_ID.incrementAndGet();
        person.setId(personId);
        REPOSITORY_MAP.put(personId, person);
        return person;
    }

    @Override
    public List<User> readAll() {
        return new ArrayList<>(REPOSITORY_MAP.values());
    }

    @Override
    public User read(int id) {
        return REPOSITORY_MAP.get(id);
    }

    @Override
    public boolean update(User person, int id) {
        if (REPOSITORY_MAP.containsKey(id)) {
            person.setId(id);
            REPOSITORY_MAP.put(id, person);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        return REPOSITORY_MAP.remove(id) != null;
    }
}
