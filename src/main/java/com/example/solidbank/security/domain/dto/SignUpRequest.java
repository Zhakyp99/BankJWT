package com.example.solidbank.security.domain.dto;

import com.example.solidbank.security.domain.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "registration request")
public class SignUpRequest {

    @Schema(description = "users name", example = "Mirzhakyp")
    @Size(min = 5, max = 50, message = "name should have 5 to 50 letters")
    @NotBlank(message = "name can not be empty blank")
    private String username;

    @Schema(description = "email", example = "mirzhakyp@gmail.com")
    @Size(min = 5, max = 255, message = "email address should have 5 to 255 symbols")
    @NotBlank(message = "can not be empty blank")
    @Email(message = "email should be in this format user@example.com")
    private String email;

    @Schema(description = "password", example = "mirzhakyp_password")
    @Size(max = 255, message = "length of password should be 255")
    private String password;
   //added lately
    private Role role;
}
