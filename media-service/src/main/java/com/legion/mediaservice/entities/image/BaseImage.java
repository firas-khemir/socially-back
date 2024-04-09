package com.legion.mediaservice.entities.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.legion.mediaservice.entities.Post;
import com.legion.mediaservice.entities.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseImage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)",length = 36, updatable = false, nullable = false)
    private UUID id;

    @Version
    private Long version;

    @NotBlank()
    @NotNull()
    private String hq_url;

    @NotBlank()
    @NotNull()
    private String mq_url;

    @NotBlank()
    @NotNull()
    private String lq_url;

    @ManyToMany
    @JoinTable(name = "image_tag",
        joinColumns = @JoinColumn(name = "image_id"),
        inverseJoinColumns = @JoinColumn(name ="tag_id"))
    private Set<Tag> tags = new HashSet<>();


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;

}
