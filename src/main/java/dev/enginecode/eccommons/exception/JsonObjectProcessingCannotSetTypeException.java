package dev.enginecode.eccommons.exception;

import org.springframework.http.HttpStatus;

public class JsonObjectProcessingCannotSetTypeException extends EngineCodeException {
    public final static int HTTP_ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();
    public final static String HTTP_ERROR_DETAILS = HttpStatus.INTERNAL_SERVER_ERROR.toString();
    private final static String MESSAGE = "Cannot set json type: '%s', it caused error: %s";

    public JsonObjectProcessingCannotSetTypeException(String jsonType, String exceptionMessage) {
        super(String.format(MESSAGE, jsonType, exceptionMessage));
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