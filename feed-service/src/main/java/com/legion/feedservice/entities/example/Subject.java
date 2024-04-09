package com.legion.feedservice.entities.example;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Data
@Node(labels = {"Subject"})
public class Subject {
    @Id
    @GeneratedValue
    private Long id;
    @Property(name = "sub_name")
    private String subName;
}
