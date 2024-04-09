package com.legion.accountservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class MinUserResponseDTO {

    private UUID id;

    @NotBlank
    private String username;

    private String  photo;
}

