package com.legion.commentservice.services;

import com.legion.commentservice.models.CommentDTO;
import com.legion.commentservice.models.UserDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<UserDTO> getUserById(UUID id);


}
