package com.ishingarov.person.model;

import java.util.Map;

public record ValidationErrorResponse(
        String message,
        Map<String, String> errors
) implements ErrorWithMessage {

}
