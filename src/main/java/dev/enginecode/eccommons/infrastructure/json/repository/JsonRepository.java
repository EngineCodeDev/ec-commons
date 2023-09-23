package dev.enginecode.eccommons.infrastructure.json.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.enginecode.eccommons.infrastructure.json.model.DataRecord;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public class JsonRepository {
    private final DataRecordJpaRepository jpaRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    JsonRepository(final DataRecordJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public <R> R findById(UUID id, Class<R> clazz) {
        DataRecord dataRecord = jpaRepository.findById(id).get();
        String data = dataRecord.getName();

        try {
            return objectMapper.readValue(data, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}



