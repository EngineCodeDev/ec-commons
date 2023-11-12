package dev.enginecode.eccommons.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends EngineCodeException {
    public final static int HTTP_ERROR_CODE = HttpStatus.NOT_FOUND.value();
    public final static String HTTP_ERROR_DETAILS = HttpStatus.NOT_FOUND.toString();
    private final static String MESSAGE = "Resource with id: '%s' not found!";

    public ResourceNotFoundException(String id) {
        super(String.format(MESSAGE, id));
    }

    @Override
    public int getHttpErrorCode() {
        return HTTP_ERROR_CODE;
    }

    @Override
    public String getHttpErrorDetails() {
        return HTTP_ERROR_DETAILS;
    }
}

