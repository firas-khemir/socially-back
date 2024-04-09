package com.legion.mediaservice.dto.event;


import com.legion.mediaservice.entities.event.EventRecurrenceType;
import com.legion.mediaservice.entities.event.EventType;
import com.legion.mediaservice.dto.CategoryDTO;
import com.legion.mediaservice.dto.ImageDTO;
import com.legion.mediaservice.dto.LocationDTO;
import com.legion.mediaservice.dto.UserDTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@AllArgsConstructor
@Value
@Builder
public class CreateEventDTO {

    @NotBlank(message = "Title should not be empty")
    @Size(max = 255, message = "Title should have between 0 - 255 characters")
    String name;

    @NotBlank(message = "Content should not be empty")
    @Size(max = 2500, message = "Content should have between 0 - 5000 characters")
    String details;

    EventType type;

    UserDTO creator;


    @NotNull(message = "start Dates should not be empty")
//    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Future
    LocalDateTime startDate;

    @NotNull(message = "Duration should be specified")
    Integer duration;

    Boolean isRecurring;

    @NotNull(message = "EnumField cannot be null")
    @Enumerated(EnumType.STRING)
    EventRecurrenceType recurrenceType;

//    Boolean isStartingTimeVariable;
//
//    LocalTime earliestStartTime;
//
//    LocalTime latestStartTime;

    Integer capacity;

    LocationDTO location;

    @NotEmpty()
    Set<@Valid ImageDTO> images;

    Set<CategoryDTO> categories;

}
