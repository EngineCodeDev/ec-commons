package dev.enginecode.eccommons.handler;

import dev.enginecode.eccommons.exception.EngineCodeExceptionGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new LinkedHashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> errors.put(
                        ((FieldError) error).getField(),
                        error.getDefaultMessage()
                )
        );
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(
                                EngineCodeExceptionGroup.VALIDATION_ERROR.get(),
                                exception.getMessage(),
                                errors
                        )
                );
    }
}
