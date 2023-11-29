package dev.enginecode.eccommons.handler;

import java.util.List;

public record ErrorResponse(String code, String message, List<String> globalErrors, List<FieldError> fieldErrors) {
    public ErrorResponse(String code, String message) {
        this(code, message, List.of(), List.of());
    }

    public boolean hasGlobalErrors() {
        return !globalErrors.isEmpty();
    }

    public boolean hasFieldErrors() {
        return !fieldErrors.isEmpty();
    }


    record FieldError(String field, String errorCode, String defaultMessage) {
    }

}
