package dev.enginecode.eccommons.infrastructure.json.repository.mapping;

public interface ObjectMapping {
    <R> R read(String data, Class<R> clazz);

    <R> String write(R object, Class<R> clazz);
}
