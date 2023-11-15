package dev.enginecode.eccommons.exception;

import org.springframework.http.HttpStatus;

public class JsonObjectProcessingException extends EngineCodeException {
    public final static String CANNOT_SET_TYPE_DETAILED = "Cannot set json type: '%s', it caused error: %s";
    public final static String CANNOT_SET_TYPE = "Internal error for data type in database processing.";
    private final static int HTTP_ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public JsonObjectProcessingException(ExceptionGroup exceptionGroup, String message, Throwable cause) {
        super(exceptionGroup, message, cause);
    }

    public JsonObjectProcessingException(ExceptionGroup exceptionGroup, String message) {
        super(exceptionGroup, message);
    }

    @Override
    public int getHttpErrorCode() {
        return HTTP_ERROR_CODE;
    }
}