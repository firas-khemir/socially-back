package com.legion.feedservice.repositories.example;

import java.util.List;

import com.legion.feedservice.entities.example.Student;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends Neo4jRepository<Student, Long> {

	List<Student> findByName(String name);

	List<Student> findByNameAndBirthYear(String name, Integer birthYear);

	List<Student> findByNameOrBirthYear(String name, Integer birthYear);

	List<Student> findByBirthYearIn(List<Integer> list);

	@Query("match (st:Student) where st.name = $name and "
			+ "st.birth_year = $birth_year return st")
	List<Student> getByNameAndBirthYear(String name,
			@Param("birth_year") Integer birthYear);

	List<Student> findByNameLike(String name);

	List<Student> findByNameStartsWith(String name);


}
