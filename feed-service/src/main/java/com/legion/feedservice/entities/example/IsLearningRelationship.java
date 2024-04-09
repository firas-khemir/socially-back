package com.legion.feedservice.entities.example;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Data
@RelationshipProperties
public class IsLearningRelationship {

    @Id
    @GeneratedValue
    private Long id;

    private Long mark;

    @TargetNode
    private Subject subject;
}
