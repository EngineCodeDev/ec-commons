package dev.enginecode.eccommons.infrastructure.json.repository;

import dev.enginecode.eccommons.infrastructure.json.model.TableAnnotatedRecord;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

public interface JsonRepository<ID extends Serializable> {
    <R extends TableAnnotatedRecord> R findById(ID id, Class<R> clazz);

    @Entity
    class DataRecord<ID extends Serializable> {
        @Id
        private ID id;
        private String data;
    }
}
