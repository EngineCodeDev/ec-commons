package dev.enginecode.eccommons.exception;

public class TableNotFoundException extends RuntimeException {
    private final ErrorCode code;

    public TableNotFoundException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}
