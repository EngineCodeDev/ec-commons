package dev.enginecode.eccommons.exception;

public class JsonObjectProcessingException extends RuntimeException {
    private final ErrorCode code;

    public JsonObjectProcessingException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}
