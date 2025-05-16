package com.maksymilianst.lightweights.auth.dto;

public record AuthenticationRequest(
        String email, String password
) {
}
