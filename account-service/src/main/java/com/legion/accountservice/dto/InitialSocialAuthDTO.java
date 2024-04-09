package com.legion.accountservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InitialSocialAuthDTO{
    @NotBlank
    private String tokenId;
}
