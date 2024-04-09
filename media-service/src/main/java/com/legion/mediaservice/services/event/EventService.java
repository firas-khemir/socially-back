package com.legion.mediaservice.services.event;

import com.legion.mediaservice.dto.event.CreateEventDTO;
import com.legion.mediaservice.dto.event.EventDTO;
import com.legion.mediaservice.dto.feed.FeedDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;


public interface EventService {
    EventDTO createEvent(CreateEventDTO eventDTO, String uid);

    Page<EventDTO> listEvents(String creatorUid, String eventContent, Integer page, Integer pageSize);

    FeedDTO getFeed(Integer page, Integer pageSize);

    Optional<EventDTO> getEventById(UUID id);

    Optional<EventDTO> updateEventById(UUID eventId, EventDTO event);

    Optional<EventDTO> participateInEvent(UUID eventId, String uid);

    Boolean deleteById(UUID eventId);

    Optional<EventDTO> patchEventById(UUID eventId, EventDTO eventDTO);
}
