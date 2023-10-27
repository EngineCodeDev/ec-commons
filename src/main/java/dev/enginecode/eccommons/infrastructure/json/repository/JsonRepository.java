package dev.enginecode.eccommons.infrastructure.json.repository;

import dev.enginecode.eccommons.infrastructure.json.model.TableAnnotatedRecord;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

public interface JsonRepository<ID extends Serializable> {
    <R extends TableAnnotatedRecord<ID>> R findById(ID id, Class<R> clazz);
    <R extends TableAnnotatedRecord<ID>> void save(R object, Class<R> clazz);

    @Entity
    record DataRecord<ID extends Serializable>(@Id ID id, String data) {
        public DataRecord() {
            this(null, null);
        }
    }
}
