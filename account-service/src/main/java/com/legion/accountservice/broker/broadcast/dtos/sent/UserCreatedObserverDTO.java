package com.legion.accountservice.broker.broadcast.dtos.sent;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class UserCreatedObserverDTO {

    UUID id;

    String uid;

    String username;

    String photo;

    Boolean isVerified;

    Boolean isHotshot;

    LocalDateTime createdDate;

    LocalDateTime lastModifiedDate;

}
