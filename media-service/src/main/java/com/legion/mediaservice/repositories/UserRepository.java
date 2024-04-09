package com.legion.mediaservice.repositories;

import com.legion.mediaservice.entities.User;
import com.legion.mediaservice.entities.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUid(String uid);

}
