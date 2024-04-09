package com.legion.apigateway.compositions.postUserComp.models.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseUserDTO {

    private UUID id;

    private String uid;

    @NotBlank
    private String username;

    @NotBlank
    private String phone;

    @Email
    @NotBlank
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthdate;

    private String  photo;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
