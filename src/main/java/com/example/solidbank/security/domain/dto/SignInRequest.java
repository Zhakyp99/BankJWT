package com.example.solidbank.security.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Authentication request")
public class SignInRequest {

    @Schema(description = "users name", example = "Mirzhakyp")
    @Size(min = 5, max = 50, message = "name should have 5 to 50 letters")
    @NotBlank(message = "name can not be empty blank")
    private String username;

    @Schema(description = "password", example = "mirzhakyp_password")
    @Size(min = 8, max = 255, message = "password length should be between 8 to 255 symbols")
    @NotBlank(message = "password can not be empty")
    private String password;
}
