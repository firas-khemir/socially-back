package com.legion.mediaservice.services.user;

import com.legion.mediaservice.broker.broadcast.dtos.received.UserCreatedDTO;
import com.legion.mediaservice.entities.User;

import java.util.Optional;

public interface UserService {


    Optional<User> getUserByUid(String uid);
    void createUser(UserCreatedDTO dto);

}
