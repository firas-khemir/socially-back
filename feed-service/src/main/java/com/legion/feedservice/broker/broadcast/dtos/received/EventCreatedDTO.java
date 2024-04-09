package com.legion.feedservice.broker.broadcast.dtos.received;

import com.legion.feedservice.dto.user.UserDTO;
import com.legion.feedservice.entities.event.EventRecurrenceType;
import com.legion.feedservice.entities.event.EventType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventCreatedDTO {

    @NotBlank(message = "Id should not be empty")
    UUID id;

    @NotBlank(message = "Title should not be empty")
    @Size(max = 255, message = "Title should have between 0 - 255 characters")
    String name;

    @NotBlank(message = "Content should not be empty")
    @Size(max = 2500, message = "Content should have between 0 - 5000 characters")
    String details;

    EventType type;

    String creatorUid;

//    @NotEmpty(message = "start Dates should not be empty")
    @NotNull()
    LocalDateTime startDate;

    @NotNull(message = "Duration should be specified")
    Integer duration;

    Boolean isRecurring;

    @NotNull(message = "EnumField cannot be null")
    @Enumerated(EnumType.STRING)
    EventRecurrenceType recurrenceType;

    Integer capacity;

    Set<String> categories = new HashSet<>();

    LocalDateTime createdDate;

    LocalDateTime lastModifiedDate;
}


