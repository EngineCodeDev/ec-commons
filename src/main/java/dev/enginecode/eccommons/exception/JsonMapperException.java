package dev.enginecode.eccommons.exception;

import org.springframework.http.HttpStatus;

public class JsonMapperException extends EngineCodeException {
    //TODO: less details
    //TODO: add more generic constructor
    public final static String CANNOT_DESERIALIZE = "Cannot deserialize data: '%s' to class: '%s', it caused error: %s";
    public final static String CANNOT_SERIALIZE = "Cannot serialize object: '%s', class: '%s' to json, it caused error: %s";
    private final static int HTTP_ERROR_CODE = HttpStatus.UNPROCESSABLE_ENTITY.value();

    public JsonMapperException(String message, String problematicItem, String problematicClassName, Throwable exception) {
        super(String.format(message, problematicItem, problematicClassName, exception.getMessage()));
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
//    public ExceptionGroup getEngineCodeError() {
//        if (getMessage().startsWith(CANNOT_DESERIALIZE.substring(18))) {
//            return EngineCodeExceptionGroup.CANNOT_DESERIALIZE_TO_GIVEN_CLASS;
//        } else if (getMessage().startsWith(CANNOT_SERIALIZE.substring(18))) {
//            return EngineCodeExceptionGroup.CANNOT_SERIALIZE_TO_JSON;
//        }
//        return EngineCodeExceptionGroup.DEFAULT_COMMON_ERROR;
//    }
}