package com.legion.accountservice.repository;

import com.legion.accountservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);


    Page<User> findByDisplayNameContainingOrUsernameContaining(String fullName, String username, Pageable pageable);


    Optional<User> findByUid(String uid);

}
