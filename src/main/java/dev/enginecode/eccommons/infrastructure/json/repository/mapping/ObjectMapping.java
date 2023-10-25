package dev.enginecode.eccommons.infrastructure.json.repository.mapping;

public interface ObjectMapping {
    <R> R read(String data, Class<R> clazz);
}
