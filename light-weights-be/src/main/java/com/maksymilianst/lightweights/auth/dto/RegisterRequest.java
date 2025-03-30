package com.maksymilianst.lightweights.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Nickname is required")
        String nickname,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must contain min. 8 characters")
        String password
) {
}
