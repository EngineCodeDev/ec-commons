package dev.enginecode.eccommons.exception;

import org.springframework.http.HttpStatus;

public class JsonObjectProcessingException extends EngineCodeException {
    public final static int HTTP_ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();
    public final static String HTTP_ERROR_DETAILS = HttpStatus.INTERNAL_SERVER_ERROR.toString();
    public final static String CANNOT_SET_TYPE = "Cannot set json type: '%s', it caused error: %s";
    public final static String CANNOT_DESERIALIZE = "Cannot deserialize data to class: '%s', it caused error: %s";
    public final static String CANNOT_SERIALIZE = "Cannot serialize class: '%s' to json, it caused error: %s";

    public JsonObjectProcessingException(String message,String problematicItem, Throwable exception) {
        super(String.format(message, problematicItem, exception.getMessage()));
    }
    public JsonObjectProcessingException(String message, String problematicItem, String problematicClassName, Throwable exception) {
        super(String.format(message, problematicItem, problematicClassName, exception.getMessage()));
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