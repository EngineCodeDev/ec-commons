package dev.enginecode.eccommons.handler;

import dev.enginecode.eccommons.exception.EngineCodeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(EngineCodeException.class)
    public ResponseEntity<ErrorResponse> handle(EngineCodeException exception) {
        return ResponseEntity
                .status(exception.getHttpErrorCode())
                .body(new ErrorResponse(exception.getExceptionGroup(), exception.getMessage()));
    }
}
