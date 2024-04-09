package com.legion.mediaservice.entities.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.legion.mediaservice.entities.Tag;
import com.legion.mediaservice.entities.event.Event;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class StoryImage extends BaseImage {

    @Builder
    public StoryImage(UUID id, Long version, String hq_url, String mq_url, String lq_url, Set<Tag> tags,
                      LocalDateTime createdDate, LocalDateTime lastModifiedDate, Event event) {
        super(id, version, hq_url, mq_url, lq_url, tags, createdDate, lastModifiedDate);
        this.event = event;
    }

    @ManyToOne
    @JsonIgnore
    private Event event;



}
