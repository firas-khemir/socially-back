package com.legion.mediaservice.dto.event;


import com.legion.mediaservice.entities.event.EventRecurrenceType;
import com.legion.mediaservice.entities.event.EventType;
import com.legion.mediaservice.dto.CategoryDTO;
import com.legion.mediaservice.dto.ImageDTO;
import com.legion.mediaservice.dto.LocationDTO;
import com.legion.mediaservice.dto.UserDTO;
import com.legion.mediaservice.dto.feed.partials.FeedDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

@Value
public class EventDTO implements FeedDTO {

    UUID id;

    Long version;

    @NotBlank()
    String name;

    @NotBlank()
    String details;

    EventType type;

    UserDTO creator;

    LocalDateTime startDate;

    Integer duration;

    Boolean isRecurring;

    EventRecurrenceType recurrenceType;

    Boolean isStartingTimeVariable;

    LocalTime earliestStartTime;

    LocalTime latestStartTime;

    Integer capacity;

    Set<UserDTO> participants;

    Boolean isOnline;

    LocationDTO location;

    Set<ImageDTO> images;

    Set<CategoryDTO> categories;

    LocalDateTime createdDate;

    LocalDateTime lastModifiedDate;

    @Override
    public String getDtoType() {
        return null;
    }
}
