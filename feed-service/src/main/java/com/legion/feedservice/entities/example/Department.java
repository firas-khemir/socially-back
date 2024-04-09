package com.legion.feedservice.entities.example;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Data
@Node(labels = {"Department"})
public class Department {
    @Id
    @GeneratedValue
    private Long id;
    @Property(name = "dep_name")
    private String depName;
}
