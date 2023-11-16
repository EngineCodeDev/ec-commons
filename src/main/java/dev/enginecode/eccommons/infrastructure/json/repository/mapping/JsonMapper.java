package dev.enginecode.eccommons.infrastructure.json.repository.mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.enginecode.eccommons.exception.JsonMapperException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static dev.enginecode.eccommons.exception.EngineCodeExceptionGroup.INFRASTRUCTURE_ERROR;
import static dev.enginecode.eccommons.exception.JsonMapperException.CANNOT_DESERIALIZE;
import static dev.enginecode.eccommons.exception.JsonMapperException.CANNOT_DESERIALIZE_DETAILED;
import static dev.enginecode.eccommons.exception.JsonMapperException.CANNOT_SERIALIZE;
import static dev.enginecode.eccommons.exception.JsonMapperException.CANNOT_SERIALIZE_DETAILED;

public class JsonMapper implements ObjectMapping {
    private static final Logger logger = LogManager.getLogger(JsonMapper.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <R> R read(String data, Class<R> clazz) {
        try {
            return mapper.readValue(data, clazz);
        } catch (JsonProcessingException exc) {
            logger.error(String.format(CANNOT_DESERIALIZE_DETAILED, data, clazz.getName(), exc));
            throw new JsonMapperException(INFRASTRUCTURE_ERROR, CANNOT_DESERIALIZE);
        }
    }

    @Override
    public <R> String write(R object, Class<R> clazz) {
        try {
            return mapper.writerFor(clazz).writeValueAsString(object);
        } catch (JsonProcessingException exc) {
            logger.error(String.format(CANNOT_SERIALIZE_DETAILED, object.toString(), clazz.getName(), exc));
            throw new JsonMapperException(INFRASTRUCTURE_ERROR, CANNOT_SERIALIZE);
        }
    }
}