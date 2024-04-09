package com.legion.feedservice.entities.event;


import com.legion.feedservice.entities.example.IsLearningRelationship;
import com.legion.feedservice.entities.user.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.neo4j.core.schema.*;


@Node
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Event {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String details;

    private EventType type;

    @Property(name = "start_date")
    private LocalDateTime startDate;

    @Property(name = "recurrence_type")
    private EventRecurrenceType recurrenceType = EventRecurrenceType.NO_RECURRENCE;

    private Integer duration;

    private Integer capacity;


    @Relationship(type = "CREATED_BY")
    private User createdBy;

    @Relationship(type = "PARTICIPANT")
    private Set<User> participants = new HashSet<>();

    @Relationship(type = "ATTENDED", direction = Relationship.Direction.OUTGOING)
    private Set<AttendedRelationship> attendedRelationshipSet;

    private Set<String> categories = new HashSet<>();

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
