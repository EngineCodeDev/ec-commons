package dev.enginecode.eccommons.exception;

import org.springframework.http.HttpStatus;

public class TableNotFoundException extends EngineCodeException {
    public final static String ANNOTATION_MISSING_DETAILED = "Lack of TableName annotation for class: '%s'";
    public final static String ANNOTATION_MISSING = "Internal error in database processing.";
    public final static String NAME_MISSING_DETAILED = "Lack of name in TableName annotation for class: '%s'";
    public final static String NAME_MISSING = ANNOTATION_MISSING;
    private final static int HTTP_ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public TableNotFoundException(ExceptionGroup exceptionGroup, String message, Throwable cause) {
        super(exceptionGroup, message, cause);
    }

    public TableNotFoundException(ExceptionGroup exceptionGroup, String message) {
        super(exceptionGroup, message);
    }

    @Override
    public int getHttpErrorCode() {
        return HTTP_ERROR_CODE;
    }
}
