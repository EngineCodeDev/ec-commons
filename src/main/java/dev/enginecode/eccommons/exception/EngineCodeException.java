package dev.enginecode.eccommons.exception;

public abstract class EngineCodeException extends RuntimeException {
    public EngineCodeException(String message) {
        super(message);
    }

    public EngineCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getHttpErrorCode();

    public abstract String getExceptionGroup();
}
