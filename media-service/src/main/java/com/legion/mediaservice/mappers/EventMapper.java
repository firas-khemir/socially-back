package com.legion.mediaservice.mappers;


import com.legion.mediaservice.broker.broadcast.dtos.sent.EventCreatedBrokerDTO;
import com.legion.mediaservice.dto.UserDTO;
import com.legion.mediaservice.entities.User;
import com.legion.mediaservice.entities.category.Category;
import com.legion.mediaservice.entities.event.Event;
import com.legion.mediaservice.dto.event.CreateEventDTO;
import com.legion.mediaservice.dto.event.EventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(source = "images", target = "images")
    @Mapping(source = "categories", target = "categories")
    @Mapping(source = "participants", target = "participants")
    EventDTO eventToEventDTO(Event event);

    @Mapping(source = "images", target = "images")
    @Mapping(source = "categories", target = "categories")
    @Mapping(source = "participants", target = "participants")
    Event eventDtoToEvent(EventDTO dto);

    @Mapping(source = "images", target = "images")
    @Mapping(source = "categories", target = "categories")
    Event createdEventDtoToEvent(CreateEventDTO dto);


    @Mapping(source = "categories", target = "categories", qualifiedByName = "mapCategoriesToStrings")
    @Mapping(source = "creator", target = "creatorUid", qualifiedByName = "getIdFromUserDto")
    EventCreatedBrokerDTO eventToCreatedEventDto(Event event);


    @Named("mapCategoriesToStrings")
    default Set<String> mapCategoriesToStrings(Set<Category> categories) {
        if (categories == null) {
            return null;
        }
        return categories.stream()
            .map(category -> category.getSpecificType() != null ?
                category.getSpecificType().toString() :
                category.getType().toString())
            .collect(Collectors.toSet());
    }

    @Named("getIdFromUserDto")
    default String getIdFromUserDto(User dto) {
        if (dto == null) {
            return null;
        }
        return dto.getUid();
    }

}
