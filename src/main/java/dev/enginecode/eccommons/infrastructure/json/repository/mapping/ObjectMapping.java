package dev.enginecode.eccommons.infrastructure.json.repository.mapping;

import dev.enginecode.eccommons.exception.EngineCodeException;

public interface ObjectMapping {
    <R> R read(String data, Class<R> clazz) throws EngineCodeException;

    <R> String write(R object, Class<R> clazz) throws EngineCodeException;
}
