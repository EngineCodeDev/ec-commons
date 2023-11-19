package dev.enginecode.eccommons.exception;

public enum EngineCodeExceptionGroup implements ExceptionGroup {
    INFRASTRUCTURE_ERROR,
    VALIDATION_ERROR,
    DATA_STRUCTURES_ERROR,
    APPLICATION_USECASE_ERROR,
    APPLICATION_DOMAIN_ERROR;


    @Override
    public String get() {
        return this.name();
    }
}
