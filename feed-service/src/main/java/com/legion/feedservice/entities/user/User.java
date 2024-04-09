package com.legion.feedservice.entities.user;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@Node("User")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    private UUID id;

    private String uid;

    private String username;

    private String photo;

    @Property(name = "is_verified")
    private Boolean isVerified;

    @Property(name = "is_hotshot")
    private Boolean isHotshot;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private Date deletionDate;

    @Relationship(type = "FOLLOW", direction = Relationship.Direction.INCOMING)
    private Set<FollowRelationship> followers = new HashSet<>();

    @Relationship(type = "FOLLOW", direction = Relationship.Direction.OUTGOING)
    private Set<FollowRelationship> following = new HashSet<>();

}
