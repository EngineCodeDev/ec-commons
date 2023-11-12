package dev.enginecode.eccommons.exception;

import org.springframework.http.HttpStatus;

public class TableNotFoundNameMissingException extends EngineCodeException {
    public final static int HTTP_ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();
    public final static String HTTP_ERROR_DETAILS = HttpStatus.INTERNAL_SERVER_ERROR.toString();
    private final static String MESSAGE = "Lack of name in TableName annotation for class: '%s'";

    public TableNotFoundNameMissingException(String className) {
        super(String.format(MESSAGE, className));
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
