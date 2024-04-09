package com.legion.accountservice.mappers;

import com.legion.accountservice.dto.MinUserResponseDTO;
import com.legion.accountservice.entities.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserResponseMapper {

    MinUserResponseDTO userToMinUserResponseDTO(User user);
    User minUserResponseDTOToUser(MinUserResponseDTO dto);

}
