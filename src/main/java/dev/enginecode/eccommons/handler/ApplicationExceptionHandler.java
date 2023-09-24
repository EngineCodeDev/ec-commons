package dev.enginecode.eccommons.handler;

import dev.enginecode.eccommons.exception.JsonNotDeserializedException;
import dev.enginecode.eccommons.exception.ResourceNotFoundException;
import dev.enginecode.eccommons.exception.TableNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(ResourceNotFoundException e) {
        return ResponseEntity
                .status( e.getCode().getHttpStatus() )
                .body( new ErrorResponse(e.getCode().toString(),e.getMessage()) );
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> handle(SQLException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(JsonNotDeserializedException.class)
    public ResponseEntity<ErrorResponse> handle(JsonNotDeserializedException e) {
        return ResponseEntity
                .status(e.getCode().getHttpStatus())
                .body(new ErrorResponse(e.getCode().toString(), e.getMessage()));
    }

    @ExceptionHandler(TableNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(TableNotFoundException e) {
        return ResponseEntity
                .status(e.getCode().getHttpStatus())
                .body(new ErrorResponse(e.getCode().toString(), e.getMessage()));
    }

}
