package com.legion.accountservice.mappers;

import com.legion.accountservice.dto.SocialAuthDTO;
import com.legion.accountservice.entities.User;
import org.mapstruct.Mapper;

@Mapper
public interface SocialAuthDTOMapper {

    SocialAuthDTO userToSocialAuthDto(User user);
    User socialAuthDtoToUser(SocialAuthDTO dto);
}
