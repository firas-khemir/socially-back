package com.legion.accountservice.mappers;

import com.legion.accountservice.dto.BaseUserDTO;
import com.legion.accountservice.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BaseUserDTOMapper {
    BaseUserDTO userToBaseUserDTO(User user);
    User baseUserDTOToUser(BaseUserDTO dto);

}
