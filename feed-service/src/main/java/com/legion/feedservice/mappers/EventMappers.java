package com.legion.feedservice.mappers;


import com.legion.feedservice.broker.broadcast.dtos.received.EventCreatedDTO;
import com.legion.feedservice.dto.event.EventDTO;
import com.legion.feedservice.entities.event.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EventMappers {
    EventMappers INSTANCE = Mappers.getMapper(EventMappers.class);

    EventDTO eventToEventDTO(Event event);

    @Mapping(source = "categories", target = "categories")
    @Mapping(source = "participants", target = "participants")
    Event eventDtoToEvent(EventDTO dto);

    @Mapping(source = "categories", target = "categories")
    @Mapping(source = "creatorUid", target = "createdBy", ignore = true)
    Event createdEventDtoToEvent(EventCreatedDTO dto);

}
