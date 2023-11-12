package dev.enginecode.eccommons.exception;

import dev.enginecode.eccommons.infrastructure.json.errors.EngineCodeErrors;
import dev.enginecode.eccommons.infrastructure.json.errors.ErrorCode;
import org.springframework.http.HttpStatus;

public class JsonMapperException extends EngineCodeException {
    public final static String CANNOT_DESERIALIZE = "Cannot deserialize data: '%s' to class: '%s', it caused error: %s";
    public final static String CANNOT_SERIALIZE = "Cannot serialize object: '%s', class: '%s' to json, it caused error: %s";
    private final static int HTTP_ERROR_CODE = HttpStatus.UNPROCESSABLE_ENTITY.value();
    private final static String HTTP_ERROR_DETAILS = HttpStatus.UNPROCESSABLE_ENTITY.toString();

    public JsonMapperException(String message, String problematicItem, String problematicClassName, Throwable exception) {
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

    @Override
    public ErrorCode getEngineCodeError() {
        if (getMessage().startsWith(CANNOT_DESERIALIZE.substring(18))) {
            return EngineCodeErrors.CANNOT_DESERIALIZE_TO_GIVEN_CLASS;
        } else if (getMessage().startsWith(CANNOT_SERIALIZE.substring(18))) {
            return EngineCodeErrors.CANNOT_SERIALIZE_TO_JSON;
        }
        return EngineCodeErrors.DEFAULT_COMMON_ERROR;
    }
}