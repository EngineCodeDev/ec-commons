package dev.enginecode.eccommons.infrastructure.json.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;
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
import static dev.enginecode.eccommons.exception.JsonObjectProcessingException.CANNOT_SET_TYPE;
import static dev.enginecode.eccommons.exception.JsonObjectProcessingException.CANNOT_SET_TYPE_DETAILED;
import static dev.enginecode.eccommons.exception.ResourceNotFoundException.NOT_FOUND_FOR_ID;
import static dev.enginecode.eccommons.exception.ResourceNotFoundException.NOT_FOUND_FOR_ID_DETAILED;
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
        ).orElseGet(() -> {
            logger.error(String.format(NOT_FOUND_FOR_ID_DETAILED, id.toString()));
            throw new ResourceNotFoundException(INFRASTRUCTURE_ERROR, String.format(NOT_FOUND_FOR_ID_DETAILED, id));
        });
    }

    <R extends TableAnnotatedRecord<ID>> List<String> getDataRecordsByEntry(String entryKey, String entryValue, Class<R> clazz) {
        String tableName = getTableName(clazz);
        QJsonRepository_DataRecord record = new QJsonRepository_DataRecord(tableName);

        String conditionTemplate = """
                    {0} -> 'entries' @> (
                        SELECT jsonb_agg(jsonb_build_object('key', entry->'key', 'value', entry->'value'))
                        FROM jsonb_array_elements({1}->'entries') entry
                        WHERE entry->>'key' = {2} AND entry->>'value' = {3})""";

        return getDataRecordsWithCondition(
                Expressions.booleanTemplate(conditionTemplate, record.data, record.data, entryKey, entryValue),
                tableName
        );
    }

    <R extends TableAnnotatedRecord<ID>> List<String> getDataRecordsByColumnLikeIgnoreCase(
            String columnName, String searchPhrase, Class<R> clazz
    ) {
        String tableName = getTableName(clazz);
        StringPath column = Expressions.stringPath(columnName);
        return getDataRecordsWithCondition(column.likeIgnoreCase(searchPhrase), tableName);
    }

    <R extends TableAnnotatedRecord<ID>> List<String> getDataRecordsByColumn(String columnName, String value, Class<R> clazz) {
        String tableName = getTableName(clazz);
        StringPath column = Expressions.stringPath(columnName);
        return getDataRecordsWithCondition(column.eq(value), tableName);
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

    private List<String> getDataRecordsWithCondition(Predicate condition, String tableName) {
        QJsonRepository_DataRecord record = new QJsonRepository_DataRecord(tableName);

        return databaseConnection.getQueryFactory()
                .select(record.data)
                .from(record)
                .where(condition)
                .fetch();
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
            logger.error(String.format(CANNOT_SET_TYPE_DETAILED, jsonType, exc), exc);
            throw new JsonObjectProcessingException(INFRASTRUCTURE_ERROR, CANNOT_SET_TYPE);
        }
        return pgObject;
    }
}
