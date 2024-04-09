package com.legion.feedservice.broker.broadcast.dtos.received;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedDTO {

    private UUID id;

    private String uid;

    private String username;

    private String photo;

    private Boolean isVerified;

    private Boolean isHotshot;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
