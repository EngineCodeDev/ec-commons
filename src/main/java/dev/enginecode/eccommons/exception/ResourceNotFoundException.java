package dev.enginecode.eccommons.exception;

import dev.enginecode.eccommons.infrastructure.json.errors.EngineCodeErrors;
import dev.enginecode.eccommons.infrastructure.json.errors.ErrorCode;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends EngineCodeException {
    private final static int HTTP_ERROR_CODE = HttpStatus.NOT_FOUND.value();
    private final static String HTTP_ERROR_DETAILS = HttpStatus.NOT_FOUND.toString();
    private final static String NOT_FOUND_FOR_ID = "Resource with id: '%s' not found!";
    private final static String NOT_FOUND_ANY = "Any resource was not not found!";

    public ResourceNotFoundException(String id) {
        super(String.format(NOT_FOUND_FOR_ID, id));
    }

    public ResourceNotFoundException() {
        super(String.format(NOT_FOUND_ANY));
    }

    @Override
    public int getHttpErrorCode() {
        return HTTP_ERROR_CODE;
    }

    @Override
    public String getHttpErrorDetails() {
        return HTTP_ERROR_DETAILS;
    }

    @Override
    public ErrorCode getEngineCodeError() {
        return EngineCodeErrors.RESOURCE_NOT_FOUND;
    }
}
