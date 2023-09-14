package dev.enginecode.eccommons.handler;

import dev.enginecode.eccommons.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(ResourceNotFoundException e) {
        return ResponseEntity
                .status( e.getCode().getHttpStatus() )
                .body( new ErrorResponse(e.getCode().toString(),e.getMessage()) );
    }

}
