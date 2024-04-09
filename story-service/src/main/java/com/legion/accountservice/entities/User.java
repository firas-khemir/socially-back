package com.legion.accountservice.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

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

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "photo")
    private String photo;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "is_hotshot")
    private Boolean isHotshot;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    @JsonManagedReference
    private Set<Story> stories = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;

    @Column(name = "deletion_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date deletionDate;
}
