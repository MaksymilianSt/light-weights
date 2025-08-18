package com.maksymilianst.lightweights.auth.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String generateAccessToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);


    boolean isTokenValid(String token, UserDetails userDetails);

    String extractUsername(String token);
}
