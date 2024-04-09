package com.legion.accountservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class UserProfileDTO {
    UUID id;

    String uid;

    @NotBlank
    String username;

    @NotBlank
    String phone;

    @Email
    String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate birthdate;

    String  photo;

    Integer followersCount;
    Integer followingCount;

    Boolean isFollower;
    Boolean isFollowing;

    LocalDateTime createdDate;

    LocalDateTime lastModifiedDate;
}
