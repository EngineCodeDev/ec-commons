package dev.enginecode.eccommons.infrastructure.json.repository;

import dev.enginecode.eccommons.infrastructure.json.model.DataRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface DataRecordJpaRepository extends JpaRepository<DataRecord, UUID>{

}
