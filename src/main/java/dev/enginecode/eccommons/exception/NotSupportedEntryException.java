package dev.enginecode.eccommons.exception;

import org.springframework.http.HttpStatus;

public class NotSupportedEntryException extends EngineCodeException {
    public final static String WRONG_TYPE_DETAILED = "For given %s can not create Entry.";
    public final static String WRONG_TYPE = "Internal error for data type creation in database processing.";
    private final static int HTTP_ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public NotSupportedEntryException(ExceptionGroup exceptionGroup, String message, Throwable cause) {
        super(exceptionGroup, message, cause);
    }

    public NotSupportedEntryException(ExceptionGroup exceptionGroup, String message) {
        super(exceptionGroup, message);
    }

    @Override
    public int getHttpErrorCode() {
        return HTTP_ERROR_CODE;
    }
}
