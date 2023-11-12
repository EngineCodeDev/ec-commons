package dev.enginecode.eccommons.infrastructure.json.repository;

import com.querydsl.sql.RelationalPathBase;
import dev.enginecode.eccommons.exception.EngineCodeException;
import dev.enginecode.eccommons.exception.JsonObjectProcessingException;
import dev.enginecode.eccommons.exception.ResourceNotFoundException;
import dev.enginecode.eccommons.exception.TableNotFoundException;
import dev.enginecode.eccommons.infrastructure.json.model.TableAnnotatedRecord;
import dev.enginecode.eccommons.infrastructure.json.model.TableName;
import dev.enginecode.eccommons.infrastructure.json.repository.database.DatabaseConnection;
import dev.enginecode.eccommons.infrastructure.json.repository.database.PostgresDatabaseConnection;
import org.postgresql.util.PGobject;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class DataRecordAbstractPostgresRepository<ID extends Serializable> implements JsonRepository<ID> {

    private final DatabaseConnection databaseConnection;

    public DataRecordAbstractPostgresRepository(DataSource dataSource) {
        databaseConnection = new PostgresDatabaseConnection(dataSource);
    }

    <R extends TableAnnotatedRecord<ID>> String getDataRecordById(ID id, Class<R> clazz) throws EngineCodeException {
        String tableName = getTableName(clazz);
        QJsonRepository_DataRecord record = new QJsonRepository_DataRecord(tableName);
        return Optional.ofNullable(
                databaseConnection.getQueryFactory()
                        .select(record.data)
                        .from(record)
                        .where(record.id.eq(id))
                        .fetchOne()
        ).orElseThrow(() -> new ResourceNotFoundException(id.toString()));
    }

    <R extends TableAnnotatedRecord<ID>> List<String> getAllDataRecords(Class<R> clazz) throws EngineCodeException {
        String tableName = getTableName(clazz);
        QJsonRepository_DataRecord record = new QJsonRepository_DataRecord(tableName);
        return databaseConnection.getQueryFactory()
                .select(record.data)
                .from(record)
                .fetch();
    }

    <R extends TableAnnotatedRecord<ID>> void saveDataRecord(JsonRepository.DataRecord<ID> dataRecord, Class<R> clazz) throws EngineCodeException {
        String tableName = getTableName(clazz);
        RelationalPathBase<?> record = new RelationalPathBase<>(QJsonRepository_DataRecord.class, "", null, tableName);
        PGobject postgresqlObject = convertToPostgresqlObject(dataRecord.data());

        databaseConnection.getQueryFactory()
                .insert(record)
                .values(dataRecord.id(), postgresqlObject)
                .execute();
    }


    private <R extends TableAnnotatedRecord<ID>> String getTableName(Class<R> clazz) throws EngineCodeException {
        String name = Optional.ofNullable(clazz.getAnnotation(TableName.class))
                .map(TableName::value)
                .orElseThrow(() -> new TableNotFoundException(TableNotFoundException.ANNOTATION_MISSING, clazz.getName()));
        if (name.isBlank()) {
            throw new TableNotFoundException(TableNotFoundException.NAME_MISSING, clazz.getName());
        }
        return name;
    }

    private PGobject convertToPostgresqlObject(String jsonData) throws EngineCodeException {
        PGobject pgObject = new PGobject();
        String jsonType = "jsonb";
        pgObject.setType(jsonType);
        try {
            pgObject.setValue(jsonData);
        } catch (SQLException exc) {
            throw new JsonObjectProcessingException(jsonType, exc);
        }
        return pgObject;
    }
}
