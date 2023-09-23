package dev.enginecode.eccommons.infrastructure.json.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.enginecode.eccommons.exception.JsonNotDeserializedException;
import dev.enginecode.eccommons.exception.ResourceNotFoundException;
import dev.enginecode.eccommons.infrastructure.json.model.DataRecord;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static dev.enginecode.eccommons.infrastructure.json.errors.InfrastructureErrorCode.CANNOT_DESERIALIZE_TO_GIVEN_CLASS;
import static dev.enginecode.eccommons.infrastructure.json.errors.InfrastructureErrorCode.RESOURCE_NOT_FOUND;

@Repository
public class JsonRepository {
    private final DataRecordJpaRepository jpaRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    JsonRepository(final DataRecordJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public <R> R findById(UUID id, Class<R> clazz) {
        DataRecord record = jpaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Resource with the given id not found!", RESOURCE_NOT_FOUND));
        String data = record.getData();

        try {
            return objectMapper.readValue(data, clazz);
        } catch (JsonProcessingException e) {
            throw new JsonNotDeserializedException(e.getMessage(), CANNOT_DESERIALIZE_TO_GIVEN_CLASS);
        }
    }


}



