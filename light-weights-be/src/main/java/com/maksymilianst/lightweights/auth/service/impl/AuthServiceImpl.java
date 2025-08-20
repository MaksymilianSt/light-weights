package com.maksymilianst.lightweights.auth.service.impl;

import com.maksymilianst.lightweights.auth.RefreshToken;
import com.maksymilianst.lightweights.auth.RefreshTokenRepository;
import com.maksymilianst.lightweights.auth.dto.AuthenticationRequest;
import com.maksymilianst.lightweights.auth.dto.AuthenticationResponse;
import com.maksymilianst.lightweights.auth.dto.RegisterRequest;
import com.maksymilianst.lightweights.auth.exception.InvalidJwtException;
import com.maksymilianst.lightweights.auth.service.AuthService;
import com.maksymilianst.lightweights.auth.service.JwtService;
import com.maksymilianst.lightweights.auth.validator.RegistrationValidator;
import com.maksymilianst.lightweights.user.Role;
import com.maksymilianst.lightweights.user.User;
import com.maksymilianst.lightweights.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
class AuthServiceImpl implements AuthService {
    private final static Set<Role> DEFAULT_ROLES = Set.of(Role.USER);
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RegistrationValidator registrationValidator;

    public AuthenticationResponse authenticate(AuthenticationRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.email(),
                        authRequest.password()
                )
        );

        var user = userRepository.findByEmail(authRequest.email())
                .orElseThrow();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        registrationValidator.validate(registerRequest);

        var user = User.builder()
                .email(registerRequest.email())
                .nickname(registerRequest.nickname())
                .password(passwordEncoder.encode(registerRequest.password()))
                .roles(DEFAULT_ROLES)
                .build();

        user = userRepository.save(user);
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    @Override
    public String refreshToken(String refreshToken) {
        RefreshToken tokenEntity =
                refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new InvalidJwtException("Refresh token not found"));

        if (tokenEntity.getExpiryDate().isBefore(Instant.now())) {
            throw new InvalidJwtException("Refresh token expired");
        }

        return jwtService.generateAccessToken(tokenEntity.getUser());
    }

    @Transactional
    public void logout(String refreshToken) {
        log.info("Logging out refresh token: {}", refreshToken);
        refreshTokenRepository.deleteByToken(refreshToken);
    }
}
