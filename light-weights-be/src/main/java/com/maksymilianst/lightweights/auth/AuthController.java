package com.maksymilianst.lightweights.auth;

import com.maksymilianst.lightweights.auth.dto.AuthenticationRequest;
import com.maksymilianst.lightweights.auth.dto.AuthenticationResponse;
import com.maksymilianst.lightweights.auth.dto.RegisterRequest;
import com.maksymilianst.lightweights.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.maksymilianst.lightweights.auth.service.JwtService.TOKEN_LIFE_TIME;

@RestController()
@RequestMapping(AuthController.AUTH_ENDPOINT)
@RequiredArgsConstructor()
public class AuthController {
    public final static String AUTH_ENDPOINT = "/api/auth";

    private final AuthService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthenticationRequest authRequest, HttpServletResponse response) {
        AuthenticationResponse authenticate = authService.authenticate(authRequest);
        ResponseCookie cookie = setAuthCookie(authenticate.token());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest, HttpServletResponse response) {
        AuthenticationResponse register = authService.register(registerRequest);
        ResponseCookie cookie = setAuthCookie(register.token());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    private ResponseCookie setAuthCookie(String token) {
        return ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .sameSite("None")
                .secure(false)
                .path("/")
                .maxAge(TOKEN_LIFE_TIME / 1000)
                .build();
    }
}
