package dev.enginecode.eccommons.exception;

import org.springframework.http.HttpStatus;

public class JsonObjectProcessingException extends EngineCodeException {
    //TODO: less details
    //TODO: add more generic constructor
    public final static String CANNOT_SET_TYPE = "Cannot set json type: '%s', it caused error: %s";
    private final static int HTTP_ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public JsonObjectProcessingException(String problematicItem, Throwable exception) {
        super(String.format(CANNOT_SET_TYPE, problematicItem, exception.getMessage()));
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
//        return EngineCodeExceptionGroup.CANNOT_SET_JSONB_TYPE;
//    }
}