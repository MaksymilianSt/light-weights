package com.maksymilianst.lightweights.auth.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Map;

public interface JwtService {
    long TOKEN_LIFE_TIME = Duration.ofHours(3).toMillis();

    String generateToken(UserDetails userDetails);

    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    String extractUsername(String token);
}
