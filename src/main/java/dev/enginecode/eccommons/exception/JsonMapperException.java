package dev.enginecode.eccommons.exception;

import org.springframework.http.HttpStatus;

public class JsonMapperException extends EngineCodeException {
    public final static String CANNOT_DESERIALIZE_DETAILED = "Cannot deserialize data: '%s' to class: '%s', it caused error: %s";
    public final static String CANNOT_DESERIALIZE = "Internal error for data parsing in database processing.";
    public final static String CANNOT_SERIALIZE_DETAILED = "Cannot serialize object: '%s', class: '%s' to json, it caused error: %s";
    public final static String CANNOT_SERIALIZE = CANNOT_DESERIALIZE;
    private final static int HTTP_ERROR_CODE = HttpStatus.UNPROCESSABLE_ENTITY.value();

    public JsonMapperException(ExceptionGroup exceptionGroup, String message, Throwable cause) {
        super(exceptionGroup, message, cause);
    }

    public JsonMapperException(ExceptionGroup exceptionGroup, String message) {
        super(exceptionGroup, message);
    }

    @Override
    public int getHttpErrorCode() {
        return HTTP_ERROR_CODE;
    }
}