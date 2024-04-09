package com.legion.mediaservice.mappers;


import com.legion.mediaservice.broker.broadcast.dtos.received.UserCreatedDTO;
import com.legion.mediaservice.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserCreatedEventMapper {

    UserCreatedDTO userToUserDTO(User user);

    User userDtoToUser(UserCreatedDTO dto);

}
