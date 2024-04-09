package com.legion.mediaservice.mappers;


import com.legion.mediaservice.entities.User;
import com.legion.mediaservice.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);

    User userDtoToUser(UserDTO dto);

}
