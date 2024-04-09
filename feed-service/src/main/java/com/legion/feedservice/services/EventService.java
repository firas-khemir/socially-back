package com.legion.feedservice.services;

import com.legion.feedservice.broker.broadcast.dtos.received.EventCreatedDTO;
import com.legion.feedservice.entities.event.Event;
import com.legion.feedservice.entities.user.User;
import com.legion.feedservice.mappers.EventMappers;
import com.legion.feedservice.repositories.EventRepository;
import com.legion.feedservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    private final EventMappers eventMapper;


    @KafkaListener(topics = "eventCreatedTopic")
    public void createEvent(EventCreatedDTO dto) {
        User user = userRepository.findByUid(dto.getCreatorUid()).orElse(null);
        if(user == null) {
            user = new User();
            user.setUid(dto.getCreatorUid());
            userRepository.save(user);
        }

        Event event = eventMapper.createdEventDtoToEvent(dto);
        event.setCreatedBy(user);

        eventRepository.save(event);
    }

    public void deleteEvent(UUID id) {
        eventRepository.deleteById(id);
    }

    public void participateInEvent(UUID eventId, UUID id) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found"));

        User participant = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        event.getParticipants().add(participant);
        eventRepository.save(event);
    }

    public void attendEvent(UUID eventId, UUID id) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found"));

        User attendee = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        event.getParticipants().add(attendee);
        eventRepository.save(event);
    }


}
