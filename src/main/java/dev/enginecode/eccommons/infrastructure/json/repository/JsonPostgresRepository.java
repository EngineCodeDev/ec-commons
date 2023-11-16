package dev.enginecode.eccommons.infrastructure.json.repository;

import dev.enginecode.eccommons.infrastructure.json.model.TableAnnotatedRecord;
import dev.enginecode.eccommons.infrastructure.json.repository.mapping.JsonMapper;
import dev.enginecode.eccommons.infrastructure.json.repository.mapping.ObjectMapping;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.List;

@Repository
public class JsonPostgresRepository<ID extends Serializable> extends DataRecordAbstractPostgresRepository<ID> {
    private final ObjectMapping mapper = new JsonMapper();

    JsonPostgresRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public <R extends TableAnnotatedRecord<ID>> R findById(ID id, Class<R> clazz) {
        String dataRecord = getDataRecordById(id, clazz);
        return mapper.read(dataRecord, clazz);
    }

    @Override
    public <R extends TableAnnotatedRecord<ID>> List<R> findByVirtualColumn(String columnName, String value, Class<R> clazz) {
        List<String> dataRecordsList = getDataRecordsByColumn(columnName, value, clazz);
        return dataRecordsList.stream()
                .map(dataRecord -> mapper.read(dataRecord, clazz))
                .toList();
    }

    @Override
    public <R extends TableAnnotatedRecord<ID>> List<R> findByEntryValue(String entryKey, String entryValue, Class<R> clazz) {
        List<String> dataRecordsList = getDataRecordsByEntry(entryKey, entryValue, clazz);
        return dataRecordsList.stream()
                .map(dataRecord -> mapper.read(dataRecord, clazz))
                .toList();
    }

    @Override
    public <R extends TableAnnotatedRecord<ID>> List<R> findAll(Class<R> clazz) {
        List<String> dataRecordsList = getAllDataRecords(clazz);
        return dataRecordsList.stream()
                .map(dataRecord -> mapper.read(dataRecord, clazz))
                .toList();
    }

    @Override
    public <R extends TableAnnotatedRecord<ID>> void save(R object, Class<R> clazz) {
        String data = mapper.write(object, clazz);
        DataRecord<ID> dataRecord = new DataRecord<>(object.id(), data);

        saveDataRecord(dataRecord, clazz);
    }




}
