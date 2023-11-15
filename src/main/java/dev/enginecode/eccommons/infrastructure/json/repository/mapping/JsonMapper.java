package dev.enginecode.eccommons.infrastructure.json.repository.mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.enginecode.eccommons.exception.JsonMapperException;

public class JsonMapper implements ObjectMapping {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <R> R read(String data, Class<R> clazz) {
        try {
            return mapper.readValue(data, clazz);
        } catch (JsonProcessingException exc) {
            //log.error(exc);
            throw new JsonMapperException(JsonMapperException.CANNOT_DESERIALIZE,
                    data, clazz.getName(), exc);
        }
    }

    @Override
    public <R> String write(R object, Class<R> clazz) {
        try {
            return mapper.writerFor(clazz).writeValueAsString(object);
        } catch (JsonProcessingException exc) {
            //log.error(exc);
            throw new JsonMapperException(JsonMapperException.CANNOT_SERIALIZE,
                    object.toString(), clazz.getName(), exc);
        }
    }
}