package com.legion.mediaservice.entities.event;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.legion.mediaservice.entities.Post;
import com.legion.mediaservice.entities.User;
import com.legion.mediaservice.entities.category.Category;
import com.legion.mediaservice.entities.image.EventImage;
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
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)",length = 36, updatable = false, nullable = false)
    private UUID id;

    @Version
    private Long version;

    @Size(max = 250)
    private String name;

    @Size(max = 2500)
    private String details;

    private EventType type;

    @ManyToOne
    @JsonIgnore
    User creator;

    // Events can be repeated daily, weekly, monthly or on custom days
    // this pattern can be repeated for a maximum of 1 year
    // this pattern can be repeated for a maximum of 1 year
//    @ElementCollection(targetClass = LocalDateTime.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "event_start_dates", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "start_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime startDate;

    private EventRecurrenceType recurrenceType = EventRecurrenceType.NO_RECURRENCE;

    // If delays aren't allowed this will be set to 0
    private Integer maxDelay;

    // This will be used to mark when the listeners ends
    private Integer duration;

    private Integer capacity;

    @ManyToMany
    @JoinTable(
        name = "event_participant",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participants;

    @ManyToMany
    @JoinTable(
        name = "event_attendant",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> attendants;

    // if isOnline == true this will be set to null
    @OneToOne
    private Location location;

    @Builder.Default
//    Not needed in test after adding the @Transactional annotation which prevents lazy loading
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "listeners", fetch = FetchType.EAGER)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    @JsonManagedReference
    private Set<EventImage> images = new HashSet<>();

    @ManyToMany
    @JsonManagedReference
    @JoinTable(name = "event_category",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    @JsonManagedReference
    private Set<Post> posts = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;

    public void addImage(EventImage image) {
        images.add(image);
        image.setEvent(this);
    }

    public void removeImage(EventImage image) {
        images.remove(image);
    }
}
