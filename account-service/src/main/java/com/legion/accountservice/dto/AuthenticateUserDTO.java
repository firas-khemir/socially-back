package com.legion.accountservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthenticateUserDTO extends BaseUserDTO{

    @NotBlank
    private String password;

}

