package com.maksymilianst.lightweights.auth;

import com.maksymilianst.lightweights.auth.dto.AuthenticationRequest;
import com.maksymilianst.lightweights.auth.dto.AuthenticationResponse;
import com.maksymilianst.lightweights.auth.dto.RegisterRequest;
import com.maksymilianst.lightweights.user.Role;
import com.maksymilianst.lightweights.user.User;
import com.maksymilianst.lightweights.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final static Set<Role> DEFAULT_ROLES = Set.of(Role.USER);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.email(),
                        authRequest.password()
                )
        );

        var user = userRepository.findByEmail(authRequest.email())
                .orElseThrow();
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = User.builder()
                .email(registerRequest.email())
                .nickname(registerRequest.nickname())
                .password(passwordEncoder.encode(registerRequest.password()))
                .roles(DEFAULT_ROLES)
                .build();

        user = userRepository.save(user);
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }
}
