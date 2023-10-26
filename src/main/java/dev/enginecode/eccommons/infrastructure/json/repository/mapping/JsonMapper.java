package dev.enginecode.eccommons.infrastructure.json.repository.mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.enginecode.eccommons.exception.JsonNotDeserializedException;

import static dev.enginecode.eccommons.infrastructure.json.errors.InfrastructureErrorCode.CANNOT_DESERIALIZE_TO_GIVEN_CLASS;

public class JsonMapper implements ObjectMapping {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <R> R read(String data, Class<R> clazz) {
        try {
            return mapper.readValue(data, clazz);
        } catch (JsonProcessingException exc) {
            throw new JsonNotDeserializedException(exc.getMessage(), CANNOT_DESERIALIZE_TO_GIVEN_CLASS);
        }
    }
}