package com.maksymilianst.lightweights.auth.service;

import com.maksymilianst.lightweights.auth.dto.AuthenticationRequest;
import com.maksymilianst.lightweights.auth.dto.AuthenticationResponse;
import com.maksymilianst.lightweights.auth.dto.RegisterRequest;

public interface AuthService {
    AuthenticationResponse authenticate(AuthenticationRequest authRequest);

    AuthenticationResponse register(RegisterRequest registerRequest);
}
