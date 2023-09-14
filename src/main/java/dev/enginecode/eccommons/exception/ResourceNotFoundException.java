package dev.enginecode.eccommons.exception;

public class ResourceNotFoundException extends RuntimeException {
    private final ErrorCode code;

    public ResourceNotFoundException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}
