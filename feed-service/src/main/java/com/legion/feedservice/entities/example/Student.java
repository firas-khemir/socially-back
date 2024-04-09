package com.legion.feedservice.entities.example;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

@Data
@Node(labels = {"Student"})
public class Student {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String country;

    @Relationship(type = "BELONGS_TO", direction = Relationship.Direction.OUTGOING)
    private Department department;

    @Relationship(type = "IS_LEARNING", direction = Relationship.Direction.OUTGOING)
    private List<IsLearningRelationship> isLearningRelationshipList;

    @Property(name = "birth_year")
    private Integer birthYear;

}
