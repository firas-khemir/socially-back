package com.legion.accountservice.mappers;

import com.legion.accountservice.dto.AuthenticateUserDTO;
import com.legion.accountservice.entities.User;
import org.mapstruct.Mapper;

@Mapper
public interface AuthenticationUserDTOMapper {
    AuthenticateUserDTO userToUserDTO(User user);
    User userDTOToUser(AuthenticateUserDTO dto);
}
