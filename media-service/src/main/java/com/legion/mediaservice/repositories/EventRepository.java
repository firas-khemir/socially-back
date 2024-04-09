package com.legion.mediaservice.repositories;

import com.legion.mediaservice.entities.event.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface EventRepository extends JpaRepository<Event, UUID> {

    Page<Event> findByDetailsContains(String content, Pageable pageable);
    Page<Event> findByCreatorUid(String creatorUid, Pageable pageable);
    Page<Event> findByCreator_IdEqualsAndDetailsContains(UUID creatorId, String content, Pageable pageable);

}
