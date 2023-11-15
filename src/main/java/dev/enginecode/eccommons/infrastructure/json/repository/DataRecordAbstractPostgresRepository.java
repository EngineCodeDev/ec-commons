package dev.enginecode.eccommons.infrastructure.json.repository;

import com.querydsl.sql.RelationalPathBase;
import dev.enginecode.eccommons.exception.JsonObjectProcessingException;
import dev.enginecode.eccommons.exception.ResourceNotFoundException;
import dev.enginecode.eccommons.exception.TableNotFoundException;
import dev.enginecode.eccommons.infrastructure.json.model.TableAnnotatedRecord;
import dev.enginecode.eccommons.infrastructure.json.model.TableName;
import dev.enginecode.eccommons.infrastructure.json.repository.database.DatabaseConnection;
import dev.enginecode.eccommons.infrastructure.json.repository.database.PostgresDatabaseConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.util.PGobject;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static dev.enginecode.eccommons.exception.EngineCodeExceptionGroup.INFRASTRUCTURE_ERROR;
import static dev.enginecode.eccommons.exception.TableNotFoundException.ANNOTATION_MISSING;
import static dev.enginecode.eccommons.exception.TableNotFoundException.ANNOTATION_MISSING_DETAILED;
import static dev.enginecode.eccommons.exception.TableNotFoundException.NAME_MISSING;
import static dev.enginecode.eccommons.exception.TableNotFoundException.NAME_MISSING_DETAILED;

public abstract class DataRecordAbstractPostgresRepository<ID extends Serializable> implements JsonRepository<ID> {

    private static final Logger logger = LogManager.getLogger(DataRecordAbstractPostgresRepository.class);
    private final DatabaseConnection databaseConnection;

    public DataRecordAbstractPostgresRepository(DataSource dataSource) {
        databaseConnection = new PostgresDatabaseConnection(dataSource);
    }

    <R extends TableAnnotatedRecord<ID>> String getDataRecordById(ID id, Class<R> clazz) {
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

    <R extends TableAnnotatedRecord<ID>> List<String> getAllDataRecords(Class<R> clazz) {
        String tableName = getTableName(clazz);
        QJsonRepository_DataRecord record = new QJsonRepository_DataRecord(tableName);
        return databaseConnection.getQueryFactory()
                .select(record.data)
                .from(record)
                .fetch();
    }

    <R extends TableAnnotatedRecord<ID>> void saveDataRecord(JsonRepository.DataRecord<ID> dataRecord, Class<R> clazz) {
        String tableName = getTableName(clazz);
        RelationalPathBase<?> record = new RelationalPathBase<>(QJsonRepository_DataRecord.class, "", null, tableName);
        PGobject postgresqlObject = convertToPostgresqlObject(dataRecord.data());

        databaseConnection.getQueryFactory()
                .insert(record)
                .values(dataRecord.id(), postgresqlObject)
                .execute();
    }


    private <R extends TableAnnotatedRecord<ID>> String getTableName(Class<R> clazz) {
        String name = Optional.ofNullable(clazz.getAnnotation(TableName.class))
                .map(TableName::value)
                .orElseGet(() -> {
                    logger.error(String.format(ANNOTATION_MISSING_DETAILED, clazz));
                    throw new TableNotFoundException(INFRASTRUCTURE_ERROR, ANNOTATION_MISSING);
                });
        if (name.isBlank()) {
            logger.error(String.format(NAME_MISSING_DETAILED, clazz));
            throw new TableNotFoundException(INFRASTRUCTURE_ERROR, NAME_MISSING);
        }
        return name;
    }

    private PGobject convertToPostgresqlObject(String jsonData) {
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
