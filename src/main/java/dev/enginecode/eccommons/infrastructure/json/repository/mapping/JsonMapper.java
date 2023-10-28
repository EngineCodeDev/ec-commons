package dev.enginecode.eccommons.infrastructure.json.repository.mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.enginecode.eccommons.exception.JsonObjectProcessingException;

import static dev.enginecode.eccommons.infrastructure.json.errors.InfrastructureErrorCode.CANNOT_DESERIALIZE_TO_GIVEN_CLASS;
import static dev.enginecode.eccommons.infrastructure.json.errors.InfrastructureErrorCode.CANNOT_SERIALIZE_TO_JSON;

public class JsonMapper implements ObjectMapping {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <R> R read(String data, Class<R> clazz) {
        try {
            return mapper.readValue(data, clazz);
        } catch (JsonProcessingException e) {
            throw new JsonObjectProcessingException(e.getMessage(), CANNOT_DESERIALIZE_TO_GIVEN_CLASS);
        }
    }

    @Override
    public <R> String write(R object, Class<R> clazz) {
        try {
            return mapper.writerFor(clazz).writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new JsonObjectProcessingException(e.getMessage(), CANNOT_SERIALIZE_TO_JSON);
        }
    }
}