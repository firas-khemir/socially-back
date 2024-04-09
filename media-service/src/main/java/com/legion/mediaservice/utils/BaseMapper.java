package com.legion.mediaservice.utils;

import java.util.Set;

public interface BaseMapper<ENTITY, DTO> {

    DTO toDto(ENTITY entity);
    ENTITY toEntity(DTO dto);
    Set<DTO> toDtoList(Set<ENTITY> entityList);
    Set<ENTITY> toEntityList(Set<DTO> dtoList);

}
