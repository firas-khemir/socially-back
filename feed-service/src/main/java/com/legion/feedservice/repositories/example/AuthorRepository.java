package com.legion.feedservice.repositories.example;

import com.legion.feedservice.entities.example.Author;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends Neo4jRepository<Author, Long> {
}
