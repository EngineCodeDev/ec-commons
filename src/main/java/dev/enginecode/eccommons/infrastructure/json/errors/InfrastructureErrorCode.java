package dev.enginecode.eccommons.infrastructure.json.errors;

import dev.enginecode.eccommons.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum InfrastructureErrorCode implements ErrorCode {
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND),
    CANNOT_DESERIALIZE_TO_GIVEN_CLASS(HttpStatus.UNPROCESSABLE_ENTITY);

    private final HttpStatus httpStatus;

    InfrastructureErrorCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
