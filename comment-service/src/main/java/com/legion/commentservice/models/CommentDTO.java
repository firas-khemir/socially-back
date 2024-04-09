package com.legion.commentservice.models;


import com.legion.commentservice.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommentDTO {

    private UUID id;

    private Long version;

    private String content;

    private User user;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
