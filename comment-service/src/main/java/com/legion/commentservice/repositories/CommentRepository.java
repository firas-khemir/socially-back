package com.legion.commentservice.repositories;

import com.legion.commentservice.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

    Page<Comment> findByPostId(UUID postId, Pageable pageable);
    Page<Comment> findByUserId(UUID userId, Pageable pageable);

}
