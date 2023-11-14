package dev.enginecode.eccommons.exception;

//todo: use runtimeexc
public abstract class EngineCodeException extends Exception{
    public EngineCodeException(String message) {
        super(message);
    }

    public EngineCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getHttpErrorCode();

    public abstract String getExceptionGroup();
}
