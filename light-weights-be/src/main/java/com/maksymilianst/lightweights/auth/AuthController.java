package com.maksymilianst.lightweights.auth;

import com.maksymilianst.lightweights.auth.dto.AuthenticationRequest;
import com.maksymilianst.lightweights.auth.dto.AuthenticationResponse;
import com.maksymilianst.lightweights.auth.dto.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(AuthController.AUTH_ENDPOINT)
@RequiredArgsConstructor()
public class AuthController {
    public final static String AUTH_ENDPOINT = "/api/auth";

    private final AuthService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest authRequest) {
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

}
