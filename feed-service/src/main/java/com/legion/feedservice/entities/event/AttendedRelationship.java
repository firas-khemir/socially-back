package com.legion.feedservice.entities.event;

import com.legion.feedservice.entities.user.User;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

@Data
@RelationshipProperties
public class AttendedRelationship {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "stay_duration")
    private Long stayDuration;

    private Integer rate;

    @TargetNode
    private User user;
}
