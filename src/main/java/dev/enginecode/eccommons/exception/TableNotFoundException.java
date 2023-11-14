package dev.enginecode.eccommons.exception;

import org.springframework.http.HttpStatus;

public class TableNotFoundException extends EngineCodeException {
    //TODO: less details
    //TODO: add more generic constructor
    public final static String ANNOTATION_MISSING = "Lack of TableName annotation for class: '%s'";
    public final static String NAME_MISSING = "Lack of name in TableName annotation for class: '%s'";
    private final static int HTTP_ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public TableNotFoundException(String message, String className) {
        super(String.format(message, className));
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
//        if (getMessage().startsWith(ANNOTATION_MISSING.substring(38))) {
//            return EngineCodeExceptionGroup.TABLENAME_ANNOTATION_NOT_FOUND;
//        } else if (getMessage().startsWith(NAME_MISSING.substring(38))) {
//            return EngineCodeExceptionGroup.TABLENAME_ANNOTATION_EMPTY;
//        }
//        return EngineCodeExceptionGroup.DEFAULT_COMMON_ERROR;
//    }
}
