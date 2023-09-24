package dev.enginecode.eccommons.handler;

public record ErrorResponse(
        String code,
        String message
) {
    public ErrorResponse(String message) {
        this("", message);
    }
}
