package com.legion.mediaservice.controller;

import com.legion.mediaservice.dto.event.CreateEventDTO;
import com.legion.mediaservice.dto.event.EventDTO;
import com.legion.mediaservice.dto.feed.FeedDTO;
import com.legion.mediaservice.services.event.EventServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
public class EventController {

    public static final String EVENT_PATH = "/api/media/events";
    public static final String EVENT_PATH_ID = EVENT_PATH + "/{eventId}";

    private final EventServiceImpl eventService;

    @GetMapping(value = EVENT_PATH)
    public Page<EventDTO> listEvents(@RequestParam(required = false) String creatorUid,
                                     @RequestParam(required = false) String postContent,
                                     @RequestParam(required = false) Integer page,
                                     @RequestParam(required = false) Integer pageSize) {
        return eventService.listEvents(creatorUid, postContent, page, pageSize);
    }

    @GetMapping(value = EVENT_PATH + "/get-feed")
    public FeedDTO getFeed(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer pageSize) {
        return eventService.getFeed(page, pageSize);
    }

    @GetMapping(value = EVENT_PATH + "/get-user-events/{userUid}")
    public Page<EventDTO> getUserEvents(@PathVariable("userUid") String userUid,
                                        @RequestParam(required = false) Integer pageNumber,
                                        @RequestParam(required = false) String content,
                                        @RequestParam(required = false) Integer pageSize) {
        return eventService.listEvents(userUid, content, pageNumber, pageSize);
    }

    @GetMapping(value = EVENT_PATH_ID)
    public EventDTO getEventById(@PathVariable("eventId") UUID id) {
        return eventService.getEventById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(EVENT_PATH)
    public ResponseEntity<?> createNewEvent(@Valid @RequestBody CreateEventDTO dto, Principal principal) {

        EventDTO savedEvent = eventService.createEvent(dto, principal.getName());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", EVENT_PATH + "/" + savedEvent.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(EVENT_PATH_ID + "/participate")
    public ResponseEntity<?> participateInEvent(@PathVariable("eventId") UUID eventId, Principal principal) {
        if (eventService.participateInEvent(eventId, principal.getName()).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(EVENT_PATH_ID)
    @PreAuthorize("@eventServiceImpl.isOwner(authentication, #eventId)")
    public ResponseEntity<?> updateEventByID(@PathVariable("eventId") UUID eventId,
                                             @RequestBody EventDTO dto) {

        if (eventService.updateEventById(eventId, dto).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(EVENT_PATH_ID)
    @PreAuthorize("@eventServiceImpl.isOwner(authentication, #eventId)")
    public ResponseEntity<?> deleteEvent(@PathVariable("eventId") UUID eventId) {

        if (!eventService.deleteById(eventId)) {
            throw new NotFoundException();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
