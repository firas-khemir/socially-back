package com.legion.mediaservice.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.legion.mediaservice.entities.event.Event;
import com.legion.mediaservice.entities.image.PostImage;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)",length = 36, updatable = false, nullable = false)
    private UUID id;

    @Version
    private Long version;

    @ManyToOne
    @JsonIgnore
    private Event event;

    @Size(max = 500)
    private String content;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    @JsonManagedReference
    private Set<PostImage> images = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    User creator;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;

    public void addImage(PostImage postImage) {
        images.add(postImage);
        postImage.setPost(this);
    }

    public void removeImage(PostImage postImage) {
        images.remove(postImage);
    }
}
