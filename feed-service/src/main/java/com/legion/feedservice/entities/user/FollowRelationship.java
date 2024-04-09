package com.legion.feedservice.entities.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.UUID;


@Getter
@Setter
@RelationshipProperties
public class FollowRelationship {

    @Id
    @GeneratedValue
    private Long id;

    private Integer InteractionCounter;

    @TargetNode
    private User user;

}
