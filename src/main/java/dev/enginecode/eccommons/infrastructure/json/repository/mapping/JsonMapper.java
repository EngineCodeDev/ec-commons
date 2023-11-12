package dev.enginecode.eccommons.infrastructure.json.repository.mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.enginecode.eccommons.exception.EngineCodeException;
import dev.enginecode.eccommons.exception.JsonObjectProcessingException;

public class JsonMapper implements ObjectMapping {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <R> R read(String data, Class<R> clazz) throws EngineCodeException {
        try {
            return mapper.readValue(data, clazz);
        } catch (JsonProcessingException exc) {
            //log.error(exc);
            throw new JsonObjectProcessingException(JsonObjectProcessingException.CANNOT_DESERIALIZE,
                    data, clazz.getName(), exc);
        }
    }

    @Override
    public <R> String write(R object, Class<R> clazz) throws EngineCodeException {
        try {
            return mapper.writerFor(clazz).writeValueAsString(object);
        } catch (JsonProcessingException exc) {
            //log.error(exc);
            throw new JsonObjectProcessingException(JsonObjectProcessingException.CANNOT_SERIALIZE,
                    object.toString(), clazz.getName(), exc);
        }
    }
}