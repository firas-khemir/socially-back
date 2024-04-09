package com.legion.mediaservice.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.legion.mediaservice.entities.event.Event;
import com.legion.mediaservice.entities.image.PostImage;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", unique = true, nullable = false, updatable = false)
    private UUID id;

    @Column(name = "uid", unique = true, nullable = false)
    private String uid;

    @Column(name = "display_name", nullable = false, columnDefinition = "varchar(36) default 'John Doe'")
    private String displayName;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "photo")
    private String photo;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "is_hotshot")
    private Boolean isHotshot;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    @JsonManagedReference
    private Set<Post> posts = new HashSet<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    @JsonManagedReference
    private Set<Event> events = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;

    @Column(name = "deletion_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date deletionDate;

    @ManyToMany
    @JoinTable(
        name = "event_participant",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<Event> EventsParticipation;

    @ManyToMany
    @JoinTable(
        name = "event_attendant",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<Event> EventsAttended;
}
