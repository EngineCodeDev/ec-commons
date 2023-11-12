package dev.enginecode.eccommons.handler;

import dev.enginecode.eccommons.exception.EngineCodeException;
import dev.enginecode.eccommons.exception.JsonObjectProcessingException;
import dev.enginecode.eccommons.exception.TableNotFoundAnnotationMissingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> handle(SQLException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(JsonObjectProcessingException.class)
    public ResponseEntity<ErrorResponse> handle(JsonObjectProcessingException e) {
        return ResponseEntity
                .status(e.getCode().getHttpStatus())
                .body(new ErrorResponse(e.getCode().toString(), e.getMessage()));
    }

    @ExceptionHandler(EngineCodeException.class)
    public ResponseEntity<ErrorResponse> handle(TableNotFoundAnnotationMissingException exception) {
        return ResponseEntity
                .status(exception.getHttpErrorCode())
                .body(new ErrorResponse(exception.getHttpErrorDetails(), exception.getMessage()));
    }
}
