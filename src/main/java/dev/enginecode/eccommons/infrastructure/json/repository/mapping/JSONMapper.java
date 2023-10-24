package dev.enginecode.eccommons.infrastructure.json.repository.mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONMapper implements Mapping {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <R> R deserialize(String data, Class<R> clazz) {
        try {
            return mapper.readValue(data, clazz);
        } catch (JsonProcessingException exc) {
            throw new RuntimeException(exc.getMessage());
        }
    }
}