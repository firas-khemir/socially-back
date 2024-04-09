package com.legion.mediaservice.dto.feed.partials;


import com.legion.mediaservice.entities.event.EventRecurrenceType;
import com.legion.mediaservice.entities.event.EventType;
import com.legion.mediaservice.dto.CategoryDTO;
import com.legion.mediaservice.dto.ImageDTO;
import com.legion.mediaservice.dto.LocationDTO;
import com.legion.mediaservice.dto.UserDTO;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Value
@Builder
public class FeedEventDTO implements FeedDTO {

    String dtoType = "Event";

    UUID id;

    Long version;

    String title;

    String content;

    EventType type;

    UserDTO creator;

    LocalDate startDate;

    LocalDate endDate;

    Boolean isRecurring;

    EventRecurrenceType recurrenceType;

    Boolean isStartingTimeVariable;

    LocalTime earliestStartTime;

    LocalTime latestStartTime;

    Integer duration;

    Integer capacity;

    Set<UserDTO> sharedParticipants;

    Integer participantsCount;

    Boolean isOnline;

    LocationDTO location;

    Set<ImageDTO> images = new HashSet<>();

    Set<CategoryDTO> categories = new HashSet<>();

    LocalDateTime createdDate;

    LocalDateTime lastModifiedDate;

    @Override
    public String getDtoType() {
        return dtoType;
    }

}
