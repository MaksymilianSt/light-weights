package com.maksymilianst.lightweights.auth.dto;

import java.util.List;
import java.util.Map;

public record ValidationErrorResponse(
        String message,
        Map<String, List<String>> errors
) {
}
