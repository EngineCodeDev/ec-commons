package dev.enginecode.eccommons.infrastructure.json.repository.mapping;

public interface Mapping {
    <R> R deserialize(String data, Class<R> clazz);
}
