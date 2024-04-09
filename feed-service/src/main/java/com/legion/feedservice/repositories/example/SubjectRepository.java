package com.legion.feedservice.repositories.example;

import com.legion.feedservice.entities.example.Subject;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SubjectRepository extends Neo4jRepository<Subject, Long> {

    Optional<Subject> findBySubName(String subName);

}
