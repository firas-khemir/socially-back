package com.legion.feedservice.services;

import com.legion.feedservice.entities.event.Event;
import com.legion.feedservice.repositories.EventRepository;
import com.legion.feedservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;


    public Set<Event> fetchFollowersEvents(String uid) {
        return eventRepository.findEventsCreatedByFollowedUsersOrderByInteractionCounter(uid);
    }
}
