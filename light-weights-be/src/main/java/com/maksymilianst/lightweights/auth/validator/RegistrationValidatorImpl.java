package com.maksymilianst.lightweights.auth.validator;

import com.maksymilianst.lightweights.auth.dto.RegisterRequest;
import com.maksymilianst.lightweights.auth.exception.RegistrationValidationException;
import com.maksymilianst.lightweights.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
class RegistrationValidatorImpl implements RegistrationValidator{
    private final UserRepository userRepository;

    public void validate(RegisterRequest registerRequest) {
        final Map<String, List<String>> errors = new HashMap<>();

        errors.putAll(
                validateEmail(registerRequest.email())
        );
        errors.putAll(
                validateNickname(registerRequest.nickname())
        );

        if (!errors.isEmpty()) {
            throw new RegistrationValidationException(errors);
        }
    }

    private Map<String, List<String>> validateEmail(String email) {
        final Map<String, List<String>> errors = new HashMap<>();
        final String errorName = "email";

        validateEmailNotExists(email, errors, errorName);

        return errors;
    }

    private void validateEmailNotExists(String email, Map<String, List<String>> errors, String errorName) {
        validateField(
                userRepository::existsByEmail,
                email,
                errors,
                errorName, "Email already exists"
        );
    }

    private Map<String, List<String>> validateNickname(String nickname) {
        final Map<String, List<String>> errors = new HashMap<>();
        final String errorName = "nickname";

        validateNicknameNotExists(nickname, errors, errorName);
        validateNicknameNotTooShort(nickname, errors, errorName);

        return errors;
    }

    private void validateNicknameNotTooShort(String nickname, Map<String, List<String>> errors, String errorName) {
        validateField(
                (nick) -> nick == null || nick.length() < 2,
                nickname,
                errors,
                errorName, "Nickname must contain at least 2 character"
        );
    }

    private void validateNicknameNotExists(String nickname, Map<String, List<String>> errors, String errorName) {
        validateField(
                userRepository::existsByNickname,
                nickname,
                errors,
                errorName, "Nickname already exists"
        );
    }

    private static <T> void validateField(Predicate<T> validator, T field, Map<String, List<String>> errors, String errorName, String errorMessage) {
        if (validator.test(field)) {
            errors.computeIfAbsent(
                    errorName, v -> new ArrayList<>()
            ).add(errorMessage);
        }
    }
}
