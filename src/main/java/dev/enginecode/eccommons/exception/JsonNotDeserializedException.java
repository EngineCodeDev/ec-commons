package dev.enginecode.eccommons.exception;

public class JsonNotDeserializedException extends RuntimeException {
    private final ErrorCode code;

    public JsonNotDeserializedException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}
