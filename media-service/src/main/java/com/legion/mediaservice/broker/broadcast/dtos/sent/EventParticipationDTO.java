package com.legion.mediaservice.broker.broadcast.dtos.sent;

import lombok.*;

import java.util.UUID;


@Value
@Builder
@AllArgsConstructor
public class EventParticipationDTO {
    UUID eventId;
    UUID userId;

}
