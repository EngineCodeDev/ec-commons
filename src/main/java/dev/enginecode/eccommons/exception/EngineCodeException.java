package dev.enginecode.eccommons.exception;

import dev.enginecode.eccommons.infrastructure.json.errors.ErrorCode;

public abstract class EngineCodeException extends Exception{
    public EngineCodeException(String message) {
        super(message);
    }

    public EngineCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getHttpErrorCode();

    public abstract String getHttpErrorDetails();

    public abstract ErrorCode getEngineCodeError();
}
