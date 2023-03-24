package com.pradeep.journeytracker.service;

import com.pradeep.journeytracker.exception.InvalidUserException;
import com.pradeep.journeytracker.model.Journey;
import com.pradeep.journeytracker.model.Stage;
import com.pradeep.journeytracker.model.User;
import com.pradeep.journeytracker.model.UserJourney;
import lombok.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserService {

    private final Map<String, User> users;

    public UserService() {
        this.users = new HashMap<>();
    }

    public User createUser(@NonNull final String name) {
        final String userId = UUID.randomUUID().toString();
        final User newUser = new User(userId, name);
        users.put(userId, newUser);
        return newUser;
    }

    public User getUserById(@NonNull final String id) {
        if(!users.containsKey(id)) {
            throw new InvalidUserException("user not found in system");
        }
        return users.get(id);
    }
}
