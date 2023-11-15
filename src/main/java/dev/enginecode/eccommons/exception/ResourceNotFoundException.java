package dev.enginecode.eccommons.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends EngineCodeException {
    public final static String NOT_FOUND_FOR_ID_DETAILED = "Resource with id: '%s' not found!";
    public final static String NOT_FOUND_FOR_ID = "Resource not found.";
    public final static String NOT_FOUND_ANY_DETAILED = "Any resource was not not found!";
    public final static String NOT_FOUND_ANY = NOT_FOUND_FOR_ID;
    private final static int HTTP_ERROR_CODE = HttpStatus.NOT_FOUND.value();

    public ResourceNotFoundException(ExceptionGroup exceptionGroup, String message, Throwable cause) {
        super(exceptionGroup, message, cause);
    }

    public ResourceNotFoundException(ExceptionGroup exceptionGroup, String message) {
        super(exceptionGroup, message);
    }

    @Override
    public int getHttpErrorCode() {
        return HTTP_ERROR_CODE;
    }
}
