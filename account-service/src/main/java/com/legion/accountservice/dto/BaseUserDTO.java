package com.legion.accountservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BaseUserDTO {

    private UUID id;

    private String uid;

    @NotBlank
    private String username;

    @NotBlank
    private String displayName;

    @NotBlank
    private String phone;

    @Email
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthdate;

    private String  photo;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
