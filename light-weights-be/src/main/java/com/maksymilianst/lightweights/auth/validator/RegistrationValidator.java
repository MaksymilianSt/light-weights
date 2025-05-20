package com.maksymilianst.lightweights.auth.validator;

import com.maksymilianst.lightweights.auth.dto.RegisterRequest;

public interface RegistrationValidator {
    void validate(RegisterRequest registration);
}
