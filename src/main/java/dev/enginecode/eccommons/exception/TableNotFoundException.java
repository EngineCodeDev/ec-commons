package dev.enginecode.eccommons.exception;

import dev.enginecode.eccommons.infrastructure.json.errors.EngineCodeErrors;
import dev.enginecode.eccommons.infrastructure.json.errors.ErrorCode;
import org.springframework.http.HttpStatus;

public class TableNotFoundException extends EngineCodeException {
    public final static String ANNOTATION_MISSING = "Lack of TableName annotation for class: '%s'";
    public final static String NAME_MISSING = "Lack of name in TableName annotation for class: '%s'";
    private final static int HTTP_ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();
    private final static String HTTP_ERROR_DETAILS = HttpStatus.INTERNAL_SERVER_ERROR.toString();

    public TableNotFoundException(String message, String className) {
        super(String.format(message, className));
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
    public ErrorCode getECErrorCode() {
        if (getMessage().startsWith(ANNOTATION_MISSING.substring(38))) {
            return EngineCodeErrors.TABLENAME_ANNOTATION_NOT_FOUND;
        } else if (getMessage().startsWith(NAME_MISSING.substring(38))) {
            return EngineCodeErrors.TABLENAME_ANNOTATION_EMPTY;
        }
        return EngineCodeErrors.DEFAULT_COMMON_ERROR;
    }
}
