package com.legion.mediaservice.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserDTO {

    private UUID id;

    private String uid;

    private String username;

    private String photo;

    private Boolean isVerified;

    private Boolean isHotshot;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
