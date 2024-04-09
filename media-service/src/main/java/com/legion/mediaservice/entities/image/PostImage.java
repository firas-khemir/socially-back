package com.legion.mediaservice.entities.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.legion.mediaservice.entities.Post;
import com.legion.mediaservice.entities.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class PostImage extends BaseImage {

    @Builder
    public PostImage(UUID id, Long version, String hq_url, String mq_url, String lq_url, Set<Tag> tags,
                      LocalDateTime createdDate, LocalDateTime lastModifiedDate, Post post) {
        super(id, version, hq_url, mq_url, lq_url, tags, createdDate, lastModifiedDate);
        this.post = post;
    }

    @ManyToOne
    @JsonIgnore
    private Post post;

}
