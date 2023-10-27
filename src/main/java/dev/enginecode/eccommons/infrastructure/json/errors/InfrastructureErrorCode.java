package dev.enginecode.eccommons.infrastructure.json.errors;

import dev.enginecode.eccommons.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum InfrastructureErrorCode implements ErrorCode {
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND),
    CANNOT_DESERIALIZE_TO_GIVEN_CLASS(HttpStatus.UNPROCESSABLE_ENTITY),
    CANNOT_SERIALIZE_TO_JSON(HttpStatus.UNPROCESSABLE_ENTITY),
    TABLENAME_ANNOTATION_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR),
    TABLENAME_ANNOTATION_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR),
    CANNOT_SET_JSONB_TYPE(HttpStatus.INTERNAL_SERVER_ERROR);

    private final HttpStatus httpStatus;

    InfrastructureErrorCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
