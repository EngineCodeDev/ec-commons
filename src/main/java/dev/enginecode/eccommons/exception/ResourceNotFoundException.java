package dev.enginecode.eccommons.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends EngineCodeException {
    //TODO: less details
    //TODO: add more generic constructor
    private final static int HTTP_ERROR_CODE = HttpStatus.NOT_FOUND.value();
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

    @Override //TODO
    public String getExceptionGroup() {
        return null;
    }

//    @Override
//    public ExceptionGroup getExceptionGroup() {
//        return EngineCodeExceptionGroup.RESOURCE_NOT_FOUND;
//    }
}
