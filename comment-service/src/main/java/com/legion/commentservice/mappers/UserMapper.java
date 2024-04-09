package com.legion.commentservice.mappers;

import com.legion.commentservice.entities.User;
import com.legion.commentservice.models.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "comments", target = "comments")
    UserDTO userToUserDTO(User user);

    @Mapping(source = "comments", target = "comments")
    User userDTOToUser(UserDTO dto);
}
