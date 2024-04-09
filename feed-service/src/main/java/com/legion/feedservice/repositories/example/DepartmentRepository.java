package com.legion.feedservice.repositories.example;

import com.legion.feedservice.entities.example.Department;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DepartmentRepository extends Neo4jRepository<Department, Long> {
    Optional<Department> findDepartmentByDepName(String depName);
}
