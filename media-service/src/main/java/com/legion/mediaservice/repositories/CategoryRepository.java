package com.legion.mediaservice.repositories;

import com.legion.mediaservice.entities.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
