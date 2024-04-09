package com.legion.feedservice.entities.event;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

import java.util.UUID;

@Builder
@Data
@RelationshipProperties
public class ReactionRelationship {

    @Id
    @GeneratedValue
    private Long id;

    private String reaction;


}
