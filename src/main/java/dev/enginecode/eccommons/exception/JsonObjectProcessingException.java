package dev.enginecode.eccommons.exception;

import dev.enginecode.eccommons.infrastructure.json.errors.EngineCodeErrors;
import dev.enginecode.eccommons.infrastructure.json.errors.ErrorCode;
import org.springframework.http.HttpStatus;

public class JsonObjectProcessingException extends EngineCodeException {
    public final static String CANNOT_SET_TYPE = "Cannot set json type: '%s', it caused error: %s";
    private final static int HTTP_ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();
    private final static String HTTP_ERROR_DETAILS = HttpStatus.INTERNAL_SERVER_ERROR.toString();

    public JsonObjectProcessingException(String problematicItem, Throwable exception) {
        super(String.format(CANNOT_SET_TYPE, problematicItem, exception.getMessage()));
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
        return EngineCodeErrors.CANNOT_SET_JSONB_TYPE;
    }
}