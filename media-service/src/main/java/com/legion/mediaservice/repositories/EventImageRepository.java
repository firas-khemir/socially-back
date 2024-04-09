package com.legion.mediaservice.repositories;

import com.legion.mediaservice.entities.image.EventImage;
import com.legion.mediaservice.entities.image.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface EventImageRepository extends JpaRepository<EventImage, UUID> {
}
