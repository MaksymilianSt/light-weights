package com.maksymilianst.lightweights.auth.dto;

import com.maksymilianst.lightweights.auth.validator.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Nickname is required")
        String nickname,

        @NotBlank(message = "Password is required")
        @Password
        String password
) {
}
