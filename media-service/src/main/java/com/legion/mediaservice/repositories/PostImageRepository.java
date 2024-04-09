package com.legion.mediaservice.repositories;

import com.legion.mediaservice.entities.image.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface PostImageRepository extends JpaRepository<PostImage, UUID> {
}
