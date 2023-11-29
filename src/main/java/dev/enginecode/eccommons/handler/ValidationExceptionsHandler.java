package dev.enginecode.eccommons.handler;

import dev.enginecode.eccommons.exception.EngineCodeExceptionGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ValidationExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exception) {
        List<String> globalErrors = exception.getGlobalErrors().stream().map(ObjectError::getDefaultMessage).toList();

        List<ErrorResponse.FieldError> fieldErrors = exception.getFieldErrors().stream()
                .map(it -> new ErrorResponse.FieldError(it.getField(), it.getCode(), it.getDefaultMessage()))
                .toList();

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(
                                EngineCodeExceptionGroup.VALIDATION_ERROR.get(),
                                exception.getMessage(),
                                globalErrors,
                                fieldErrors
                        )
                );
    }

}
