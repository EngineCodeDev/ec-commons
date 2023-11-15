package dev.enginecode.eccommons.exception;

public abstract class EngineCodeException extends RuntimeException {
    protected final ExceptionGroup exceptionGroup;

    public EngineCodeException(ExceptionGroup exceptionGroup, String message, Throwable cause) {
        super(message, cause);
        this.exceptionGroup = exceptionGroup;
    }

    public EngineCodeException(ExceptionGroup exceptionGroup, String message) {
        super(message);
        this.exceptionGroup = exceptionGroup;
    }

    public abstract int getHttpErrorCode();

    public String getExceptionGroup() {
        return exceptionGroup.get();
    }
}
