package com.maksymilianst.lightweights.auth.service;

import com.maksymilianst.lightweights.auth.exception.InvalidJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService{

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${security.jwt.issuer}")
    private String ISSUER;
    @Value("${security.jwt.audience}")
    private String AUDIENCE;
    private final static long TOKEN_LIFE_TIME = Duration.ofHours(3).toMillis();


    public String generateToken(UserDetails userDetails) {
        return generateToken(null, userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TOKEN_LIFE_TIME))
                .issuer(ISSUER)
                .audience().add(AUDIENCE).and()
                .id(UUID.randomUUID().toString())
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        final String issuer = extractClaim(token, Claims::getIssuer);
        final Set<String> audiences = extractClaim(token, Claims::getAudience);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token)
                && ISSUER.equals(issuer)
                && audiences.contains(AUDIENCE);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        final Date currentDateTime = new Date(System.currentTimeMillis());
        final Date tokenExp = extractClaim(token, Claims::getExpiration);

        return tokenExp == null || tokenExp.before(currentDateTime);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new InvalidJwtException();
        }
    }

    private SecretKey getSignInKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
