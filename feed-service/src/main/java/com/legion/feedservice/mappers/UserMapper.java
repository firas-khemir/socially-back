package com.legion.feedservice.mappers;

import com.legion.feedservice.broker.broadcast.dtos.received.UserCreatedDTO;
import com.legion.feedservice.dto.user.UserDTO;
import com.legion.feedservice.entities.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);


    UserCreatedDTO userToUserCreatedDTO(User user);

    User userCreatedDtoToUser(UserCreatedDTO dto);

}
