package com.legion.feedservice.controllers;


import com.legion.feedservice.entities.event.Event;
import com.legion.feedservice.services.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FeedController {

    public static final String FEED_PATH = "/api/feed/gen";

    private final FeedService feedService;


    @GetMapping(value = FEED_PATH)
    public Set<Event> getPostById(Principal principal) {
        log.warn(principal.getName());
        return feedService.fetchFollowersEvents(principal.getName());
    }
}
