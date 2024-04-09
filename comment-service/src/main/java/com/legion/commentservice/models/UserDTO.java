package com.legion.commentservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDTO {

    private UUID id;

    private String uid;

    private String username;

    private String photo;

    private Boolean isVerified;

    private Boolean isHotshot;

    Set<CommentDTO> comments = new HashSet<>();

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
