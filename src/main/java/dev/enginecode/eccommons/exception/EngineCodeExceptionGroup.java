package dev.enginecode.eccommons.exception;

public enum EngineCodeExceptionGroup implements ExceptionGroup {
    INFRASTRUCTURE_ERROR, //SERIAL, DESERIAL, TABLES ANN NAME, JSON,
    DATA_STRUCTURES_ERROR,
    APPLICATION_USECASE_ERROR,
    APPLICATION_DOMAIN_ERROR;


    @Override
    public String get() {
        return this.name();
    }
}
