package com.legion.mediaservice.repositories;

import com.legion.mediaservice.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface PostRepository extends JpaRepository<Post, UUID> {


    Page<Post> findByContentContains(String content, Pageable pageable);
    Page<Post> findByCreator_Id(UUID creatorId, Pageable pageable);
    Page<Post> findByCreator_IdEqualsAndContentContains(UUID creatorId, String content, Pageable pageable);
}
