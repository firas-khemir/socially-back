package com.legion.accountservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SocialAuthDTO extends BaseUserDTO{
    @NotBlank
    private String tokenId;
}
