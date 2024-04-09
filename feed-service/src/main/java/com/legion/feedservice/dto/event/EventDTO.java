package com.legion.feedservice.dto.event;


import com.legion.feedservice.entities.event.AttendedRelationship;
import com.legion.feedservice.entities.event.EventRecurrenceType;
import com.legion.feedservice.entities.event.EventType;
import com.legion.feedservice.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Value
@Builder
public class EventDTO {

    UUID id;

    String name;
    String details;
    EventType type;

    LocalDateTime startDate;
    EventRecurrenceType recurrenceType = EventRecurrenceType.NO_RECURRENCE;

    Integer duration;

    Integer capacity;

//     Location location;

    User createdBy;

    Set<User> participants = new HashSet<>();

    Set<AttendedRelationship> attendedRelationshipSet;

    Set<String> categories = new HashSet<>();

    LocalDateTime createdDate;

    LocalDateTime lastModifiedDate;
}
